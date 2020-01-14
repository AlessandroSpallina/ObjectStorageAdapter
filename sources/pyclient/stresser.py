import os
from osa_api import ObjectStorageAdapterAPI

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
def load1(osa, N1, metrics):
    id_list = []

    for i in range(N1):
        resp = osa.send_metadata_file("video_{}.mp4".format(i), "lbry inc")
        if resp.status_code == 200:
            id_list.append(resp.json()['id'])
        # log metriche qui
        #metrics.append({'http_method':'GET', })

    for i in range(len(id_list)):
        resp = osa.send_file(id_list.pop(), './files/video.mp4')
        if resp.status_code == 200:
            pass








def load2():
    pass

def load3():
    pass

def load4():
    pass







def main():
    print("============ STRESSER CLIENT ============")

    osa = ObjectStorageAdapterAPI('user@a.a', 'user', 'http://192.168.1.118:9090/fms')

    print("\n-> Reading ENV")
    N1 = os.environ.get('N1', 1)
    N2 = os.environ.get('N2', 1)
    N3 = os.environ.get('N3', 1)
    N4 = os.environ.get('N4', 1)
    P1 = os.environ.get('P1', 1)
    P2 = os.environ.get('P2', 1)
    print("* N1: {}\n* N2: {}\n* N3: {}\n* N4: {}\n* P1: {}\n* P2: {}".format(N1, N2, N3, N4, P1, P2))

    print("\n-> Testing API")
    api_test_print(osa)



    print("\n-> Load1")
    load1(osa, N1)

    osa.osa_metrics_to_csv('metrics.csv')









    print("=========================================")


  #print(osa.ping())




main()
