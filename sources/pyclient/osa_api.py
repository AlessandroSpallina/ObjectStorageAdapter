import os
import requests
from requests.auth import HTTPBasicAuth
import json
import csv

class ObjectStorageAdapterAPI:
    def __init__(self, user, pasw, endpoint):
        self.user = user
        self.pasw = pasw
        self.endpoint = endpoint
        # used to store metrics, for each request:
        # http method, payload size input, payload size output, response time (in sec), http status code
        self.metrics = []

    def _url(self, path):
        return self.endpoint + path

    def osa_metrics_to_csv(self, path):
        with open(path, 'w', newline='') as file:
            writer = csv.writer(file)
            writer.writerow(['Method', 'Payload Size In', 'Payload Size Out', 'Response Time', 'Status Code'])
            writer.writerows(self.metrics)

    def ping(self):
        headers = {'Content-Type':'application/json'}
        resp = requests.get(self._url('/ping'), headers=headers, auth=(self.user, self.pasw))
        self.metrics.append(['GET /ping', 0, len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # GET 1
    def get_file_url(self, file_id):
        headers = {'Content-Type':'application/json'}
        resp = requests.get(self._url('/{}'.format(file_id)), headers=headers, auth=(self.user, self.pasw))
        self.metrics.append(['GET /{}'.format(file_id), 0, len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # GET 2
    def get_file_list(self):
        headers = {'Content-Type':'application/json'}
        resp = requests.get(self._url('/'), headers=headers, auth=(self.user, self.pasw))
        self.metrics.append(['GET /', 0, len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # POST 3
    def send_metadata_file(self, filename, author):
        headers = {'Content-Type':'application/json'}
        payload = json.dumps({'filename':filename, 'author':author})
        resp = requests.post(self._url('/'), headers=headers, data=payload, auth=(self.user, self.pasw))
        self.metrics.append(['POST /', len(payload), len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # POST 4
    def send_file(self, file_id, path):
        files = [('file', open(path, 'rb'))]
        resp = requests.post(self._url('/{}'.format(file_id)), files=files, auth=(self.user, self.pasw))
        self.metrics.append(['POST /{}'.format(file_id), os.fstat(files[0][1].fileno()).st_size, len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # DELETE 5
    def delete_file(self, file_id):
        headers = {'Content-Type':'application/json'}
        resp = requests.delete(self._url('/{}'.format(file_id)), headers=headers, auth=(self.user, self.pasw))
        self.metrics.append(['DELETE /{}'.format(file_id), 0, len(resp.content), resp.elapsed.total_seconds(), resp.status_code])
        return resp

    # POST 6 [@findme] : not implemented
    # def register_user(self):
    #     pass
