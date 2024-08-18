from tarfile import NUL
import requests
from requests.auth import HTTPBasicAuth
import uuid
import http
import json


BASE_URL = "http://127.0.0.1:8080"

signup_payload = {
    "username": "test_user_" + uuid.uuid4().hex,
    "password": "test_user",
    "name": "test_user",
    "email": "test_user@mycompany.com" + uuid.uuid4().hex
}

headers = {
    'Content-Type': 'application/json'
}

# will be assigned after creating user
user_id = 0

# stored as int
prev_no_of_users = requests.get(BASE_URL + "/public/numberOfUsers").json()


"""----------------------------------
|    TESTING /AUTH CONTROLLERS      |
----------------------------------"""

def test_creation_of_new_user():
    # requesting to create new user
    signup_response = requests.request("POST", BASE_URL + "/auth/signup", headers=headers, data=json.dumps(signup_payload))

    assert signup_response.status_code == http.HTTPStatus.CREATED
    
    # check incremented no of user
    # mignt not work if server is getting many signup request at the same time
    assert (prev_no_of_users + 1) == requests.get(BASE_URL + "/public/numberOfUsers").json()
    
    # set new global user_id
    global user_id
    user_id = signup_response.json()["id"]


def test_authentication_of_new_user():
    # verify new user is on server
    auth_payload = {
        "username": signup_payload["username"],
        "password": signup_payload["password"]
    }
    auth_response = requests.request("POST",  BASE_URL + "/auth/authenticate", headers=headers, data=json.dumps(auth_payload))
    
    assert auth_response.status_code == http.HTTPStatus.OK


def test_bad_credential():
    auth_payload_1 = {
        "username": signup_payload["username"] + "bad",
        "password": signup_payload["password"]
    }
    auth_payload_2 = {
        "username": signup_payload["username"],
        "password": signup_payload["password"] + "bad"
    }
    
    auth_response_1 = requests.request("POST",  BASE_URL + "/auth/authenticate", headers=headers, data=json.dumps(auth_payload_1))
    assert auth_response_1.status_code == http.HTTPStatus.UNAUTHORIZED
    
    auth_response_2 = requests.request("POST",  BASE_URL + "/auth/authenticate", headers=headers, data=json.dumps(auth_payload_2))
    assert auth_response_2.status_code == http.HTTPStatus.UNAUTHORIZED


"""----------------------------------
|    TESTING /USERS CONTROLLERS     |
----------------------------------"""

def test_basic_auth():
    username = signup_payload["username"]
    password = signup_payload["password"]
    
    # check successful login with basic auth
    response = requests.get(BASE_URL + "/api/users/me", auth=HTTPBasicAuth(username, password))
    assert response.status_code == http.HTTPStatus.OK


def test_listing_of_all_users():
    #since it requires ADMIN role
    response = requests.get(BASE_URL + "/api/users", auth=HTTPBasicAuth("admin", "admin"))
    assert response.status_code == http.HTTPStatus.OK
    
    # test forbidden request as a USER
    response = requests.get(BASE_URL + "/api/users", auth=HTTPBasicAuth(signup_payload["username"], signup_payload["password"]))
    assert response.status_code == http.HTTPStatus.FORBIDDEN
    
    
def test_getting_user_as_ADMIN():
    # since it requires ADMIN role
    response = requests.get(
        BASE_URL + "/api/users/" + signup_payload["username"],
        auth=HTTPBasicAuth("admin", "admin")
    )
    assert response.status_code == http.HTTPStatus.OK
    assert response.json()["id"] == user_id
    assert response.json()["email"] == signup_payload["email"]


def test_getting_user_as_USER():
    # test forbidden request as a USER
    response  = response = requests.get(
        BASE_URL + "/api/users/" + signup_payload["username"],
        auth=HTTPBasicAuth(signup_payload["username"], signup_payload["password"])
    )
    assert response.status_code == http.HTTPStatus.FORBIDDEN


def test_deleting_user_as_a_USER():
    # test forbidden request as a USER
    response  = response = requests.delete(
        BASE_URL + "/api/users/" + signup_payload["username"],
        auth=HTTPBasicAuth(signup_payload["username"], signup_payload["password"])
    )
    assert response.status_code == http.HTTPStatus.FORBIDDEN


def test_deleting_user_as_a_ADMIN():
    # since it requires ADMIN role
    response = requests.delete(
        BASE_URL + "/api/users/" + signup_payload["username"],
        auth=HTTPBasicAuth("admin", "admin")
    )
    assert response.status_code == http.HTTPStatus.OK
    assert response.json()["id"] == user_id
    assert response.json()["email"] == signup_payload["email"]

def test_getting_deleted_user():
    response = requests.get(
        BASE_URL + "/api/users/" + signup_payload["username"],
        auth=HTTPBasicAuth("admin", "admin")
    )
    assert response.status_code == 401