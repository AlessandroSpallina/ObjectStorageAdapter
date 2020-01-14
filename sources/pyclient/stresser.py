from osa_api import ObjectStorageAdapterAPI

def load1():
    pass

def load2():
    pass

def load3():
    pass

def load4():
    pass

def export_csv():
    pass






def main():
    osa = ObjectStorageAdapterAPI('user@a.a', 'user', 'http://151.97.150.179:9090/fms')

    print("============ STRESSER CLIENT ============")
    print("Testing API:")

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
        print("* (4) DeleteFile: FAIL[{}]".format(ret))





    print("=========================================")


  #print(osa.ping())




main()
