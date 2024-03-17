FROM clojure:lein
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN lein deps
EXPOSE 8080
CMD ["lein", "run"]
