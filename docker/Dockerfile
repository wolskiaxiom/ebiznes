FROM ubuntu:18.04

ENV TZ=Europe/Warsaw

RUN apt update && apt-get upgrade -y
RUN apt install openjdk-8-jdk -y
RUN apt-get install wget dpkg -y
RUN wget https://downloads.lightbend.com/scala/2.12.0/scala-2.12.0.deb
RUN dpkg -i scala-2.12.0.deb
RUN apt-get install npm -y
#RUN apt-get install echo curl tee apt-key -y

RUN echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list 
RUN apt-get install curl -y
RUN curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add
RUN apt-get update
RUN apt-get install sbt -y

RUN useradd -ms /bin/bash wolskiaxiom
RUN adduser wolskiaxiom sudo

EXPOSE 8888

USER wolskiaxiom
WORKDIR /home/wolskiaxiom
RUN mkdir /home/wolskiaxiom/projekt

VOLUME /home/wolskiaxiom/projekt

