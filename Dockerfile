FROM clojure:lein
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN lein deps
EXPOSE 3000
CMD ["lein", "run"]
