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
    osa = ObjectStorageAdapterAPI('user@a.a', 'user', 'http://192.168.1.75:9090/fms')

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

    ret = osa.send_metadata_file('video.mp4', 'lbry inc').status_code
    if(ret == 200): # http ok
        print("* (3) SendMetadataFile: OK")
    else:
        print("* (3) SendMetadataFile: FAIL[{}]".format(ret))






    print("=========================================")


  #print(osa.ping())




main()
