# wex-challenge
A repository representing the wex coding challenge


# Dependencies

Since this solution is containerized with docker and docker-compose, you will need both to run the application

# How to run

To build the containers:

docker compose build


To start the project using docker:
docker compose up

To stop the project after running, either Ctrl+C the terminal, or running the command docker rm -f $(docker ps -q).

On development:
If you need to cleanup the docker images, run the command docker rm -v $(docker ps --filter status=exited -q) and docker rm -v -f $(docker ps -qa)

You can also run the command docker compose down -v, which should cleanup all the docker compose

# The APIs

GET /transaction

List all transactions stored on the database.

GET /transaction/{id}

List specific transaction, based on it's id.

POST /transaction

Save a transaction. 

The expected payload is:
{
    "createdDate": "2025-01-01", //A date on format yyyy-MM-dd, based on the ISO 8601 standard. Optional, if not passed, it will assume today as the transaction time
    "amount": 10.50, //A number up to 2 digits after dot
    "description": "a description" //A description. it should have at most 50 characters

}


# TroubleShooting

All the trouble shooting should work on a unix-like system, like Ubuntu

- I'm not being able to run the docker-compose up command

Sometimes, you may receive the following output:

permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get "http://%2Fvar%2Frun%2Fdocker.sock/v1.51/containers/json": dial unix /var/run/docker.sock: connect: permission denied

As a gentle reminder, you may need to add your current user on the docker group.
To do that, run the following command:
sudo usermod -aG docker $USER