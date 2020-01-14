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
    if(osa.ping().status_code == 200):
        print("* Ping: OK")
    else:
        print("* Ping: FAIL")

    






    print("=========================================")


  #print(osa.ping())




main()
