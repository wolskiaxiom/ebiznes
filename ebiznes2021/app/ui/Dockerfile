FROM ubuntu:18.04

ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone 

RUN apt update && apt install -y build-essential unzip vim git curl wget zip

RUN apt-get update &&\
	apt-get upgrade -y &&\
    apt-get install -y  software-properties-common

# JS
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get install -y nodejs
RUN npm install -g npm@latest
RUN npm install -g serve@latest
RUN apt-get install -y net-tools

EXPOSE 3000

RUN useradd -ms /bin/bash lwolski
RUN adduser lwolski sudo

USER lwolski
WORKDIR /home/lwolski/

RUN mkdir projekt
WORKDIR /home/lwolski/projekt/

COPY --chown=lwolski ./ /home/lwolski/projekt/frontend-react/
WORKDIR /home/lwolski/projekt/frontend-react
RUN bash -c "npm install"
RUN bash -c "npm run build --scripts-prepend-node-path=auto"
RUN bash -c "rm -R node_modules/" -y
CMD ["serve", "-s", "build"]