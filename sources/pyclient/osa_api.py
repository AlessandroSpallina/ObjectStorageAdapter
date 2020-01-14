import requests
from requests.auth import HTTPBasicAuth

class ObjectStorageAdapterAPI:
    def __init__(self, user, pasw, endpoint):
        self.user = user
        self.pasw = pasw
        self.endpoint = endpoint

    def _url(self, path):
        return self.endpoint + path

    def ping(self):
        #requests.get(self._url('/ping'))
        headers = {'Content-Type':'application/json'}
        return requests.get(self._url('/ping'), headers=headers, auth=(self.user, self.pasw))


    # GET 1
    def get_file_url(self):
        pass

    # GET 2
    def get_file_list(self):
        pass

    # POST 3
    def send_metadata_file(self):
        pass

    # POST 4
    def send_file(self):
        pass

    # DELETE 5
    def delete_file(self):
        pass

    # POST 6
    def register_user(self):
        pass
