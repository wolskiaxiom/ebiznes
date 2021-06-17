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
RUN apt-get install -y net-tools

EXPOSE 8080
EXPOSE 5000
EXPOSE 9000
EXPOSE 3000


RUN useradd -ms /bin/bash lwolski
RUN adduser lwolski sudo

USER lwolski
WORKDIR /home/lwolski/
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "/home/lwolski/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install java 8.0.272.hs-adpt"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install sbt 1.5.1"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install scala 2.12.13"

RUN git clone https://github.com/VundleVim/Vundle.vim.git ~/.vim/bundle/Vundle.vim
RUN git clone https://github.com/kprzystalski/vim && cp vim/.vimrc /home/lwolski/

RUN mkdir projekt
WORKDIR /home/lwolski/projekt/

COPY --chown=lwolski backend-scala /home/lwolski/projekt/backend-scala
COPY --chown=lwolski frontend-react /home/lwolski/projekt/frontend-react
WORKDIR /home/lwolski/projekt/frontend-react
RUN bash -c "npm install"
WORKDIR /home/lwolski/projekt
COPY --chown=lwolski start-processes.sh /home/lwolski/projekt/start-processes.sh
RUN chmod a+x "/home/lwolski/projekt/start-processes.sh"
CMD ["./start-processes.sh"]
#RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sbt new playframework/play-scala-seed.g8"

