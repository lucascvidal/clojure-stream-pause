# Playback API Exercise

This is an exercise using Clojure to handle playback positions storage.

## Usage

This project uses docker and docker compose. So:
```bash
docker compose up
```

This will build the app image and pull (if you haven't already) the postgres image. The way the containers are set up they don't run a command and exit, they stay up until you put them down explicitly. So in order to run the server or the tests, you have to first open up a bash session in the container:
```bash
docker exec -it playback-api bash
```

## Running the server

With the bash session opened:
```bash
lein run
```

## Running tests

Work in progress.
