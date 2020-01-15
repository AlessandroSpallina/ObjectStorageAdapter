import os
import threading
from osa_api import ObjectStorageAdapterAPI
import time
import random

'''
Call all APIs and print a message based on HTTP status code
'''
def api_test_print(osa):
    ret = osa.ping().status_code
    if(ret == 200): # http ok
        print("* Ping: OK")
    else:
        print("* Ping: FAIL[{}]".format(ret))

    ret = osa.get_file_url(2).status_code
    if(ret == 301): # http moved permanently
        print("* (1) GetFile: OK")
    else:
        print("* (1) GetFile: FAIL[{}]".format(ret))

    ret = osa.get_file_list().status_code
    if(ret == 200): # http ok
        print("* (2) GetFileList: OK")
    else:
        print("* (2) GetFileList: FAIL[{}]".format(ret))

    response = osa.send_metadata_file('video.mp4', 'lbry inc')
    ret = response.status_code
    file_id = response.json()['id']
    if(ret == 200): # http ok
        print("* (3) SendMetadataFile: OK")
    else:
        print("* (3) SendMetadataFile: FAIL[{}]".format(ret))

    ret = osa.send_file(file_id, './files/video.mp4').status_code
    if(ret == 200): # http ok
        print("* (4) SendFile: OK")
    else:
        print("* (4) SendFile: FAIL[{}]".format(ret))

    ret = osa.delete_file(file_id).status_code
    if(ret == 200): # http ok
        print("* (5) DeleteFile: OK")
    else:
        print("* (5) DeleteFile: FAIL[{}]".format(ret))

'''
Load1:
    *   N1 POST / (metadata)
    *   N1 POST /{id} (file)
'''
def load1(osa, N1):
    id_list = []
    for i in range(N1):
        resp = osa.send_metadata_file("video_{}.mp4".format(i), "lbry inc")
        if resp.status_code == 200:
            id_list.append(resp.json()['id'])
    for i in range(len(id_list)):
        resp = osa.send_file(id_list.pop(), './files/video.mp4')

def load2(osa, N2, duration_seconds):
    while duration_seconds > 0:
        for i in range(N2):
            threading.Timer(1.0, osa.get_file_list).start()
        duration_seconds -= 1
        time.sleep(1)

def load3(osa, N3, duration_seconds, probability_pick_existent, existent_file_id, non_existent_file_id):
    while duration_seconds > 0:
        for i in range(N3):
            rand = random.randrange(100)
            if rand < probability_pick_existent * 100:
                threading.Timer(1.0, osa.get_file_url, [existent_file_id]).start()
            else:
                threading.Timer(1.0, osa.get_file_url, [non_existent_file_id]).start()
        duration_seconds -= 1
        time.sleep(1)

def load4(osa, N4, probability_pick_existent, existing_files_list):
    loop_max_iters = N4
    if len(existing_files_list) < N4:
        loop_max_iters = len(existing_files_list)
    for i in range(loop_max_iters):
        rand = random.randrange(100)
        if rand < probability_pick_existent * 100:
            resp = osa.delete_file(existing_files_list.pop())
        else:
            resp = osa.delete_file(777) # assumo che file_id 777 non esista (evviva i magic number ignoranti)

def _get_existing_files_list(osa):
    files_list = []
    resp = osa.get_file_list()
    if resp.status_code == 200:
        for f in resp.json():
            files_list.append(f['id'])
    return files_list





def main():
    print("============ STRESSER CLIENT ============")

    osa = ObjectStorageAdapterAPI('user@a.a', 'user', 'http://151.97.159.253:9090/fms')

    print("\n-> Reading ENV")
    N1 = int(os.environ.get('N1', 1))
    N2 = int(os.environ.get('N2', 1))
    N3 = int(os.environ.get('N3', 1))
    N4 = int(os.environ.get('N4', 1))
    P1 = float(os.environ.get('P1', 1))
    P2 = float(os.environ.get('P2', 1))
    print("* N1: {}\n* N2: {}\n* N3: {}\n* N4: {}\n* P1: {}\n* P2: {}".format(N1, N2, N3, N4, P1, P2))

    print("\n-> Testing API")
    api_test_print(osa)
    load1(osa, N1)
    print("\n-> Load1")
    load2(osa, N2, 30)
    print("\n-> Load2")
    load3(osa, N3, 30, P1, 2, 3)
    print("\n-> Load3")
    load4(osa, N4, P2, _get_existing_files_list(osa))
    print("\n-> Load4")
    osa.osa_metrics_to_csv('metrics.csv')
    print("\n-> Csv metrics")
    print("=========================================")


main()
