FROM ubuntu:18.04

ENV TZ=Europe/Warsaw
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone 

RUN apt update && apt install -y build-essential unzip vim git curl wget zip

RUN apt-get update &&\
	apt-get upgrade -y &&\
    apt-get install -y  software-properties-common

# JS
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get install -y nodejs
RUN npm install -g npm@latest
RUN npm install -g serve@latest
RUN apt-get install -y net-tools

USER root
RUN id -u lwolski 1>/dev/null 2>&1 || (( getent group 0 1>/dev/null 2>&1 || ( type groupadd 1>/dev/null 2>&1 && groupadd -g 0 root || addgroup -g 0 -S root )) && ( type useradd 1>/dev/null 2>&1 && useradd --system --create-home --uid 1001 --gid 0 lwolski || adduser -S -u 1001 -G root lwolski ))
USER 1001:0

USER lwolski
WORKDIR /home/lwolski/
RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "/home/lwolski/.sdkman/bin/sdkman-init.sh"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install java 8.0.302-open"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install sbt 1.6.2"
RUN bash -c "source /home/lwolski/.sdkman/bin/sdkman-init.sh && sdk install scala 2.12.15"

RUN mkdir projekt
WORKDIR /home/lwolski/projekt/

COPY --chown=lwolski:root ./ /home/lwolski/projekt/
WORKDIR /home/lwolski/projekt/ui
RUN bash -c "npm install"
RUN bash -c "npm run build --scripts-prepend-node-path=auto"

USER root
RUN chown lwolski:root /opt
USER lwolski
#RUN mkdir /opt/stage
#CMD ["serve", "-s", "build"]

WORKDIR /home/lwolski/projekt
RUN bash -c "/home/lwolski/.sdkman/candidates/sbt/1.6.2/bin/sbt -java-home /home/lwolski/.sdkman/candidates/java/current stage"
RUN bash -c "cp -R /home/lwolski/projekt/target/universal/stage /opt/stage"
RUN bash -c "rm -R /home/lwolski/projekt/" -y
#
EXPOSE 9000
RUN chmod a+x "/opt/stage/bin/ebiznes-scala-and-play"
CMD ["/opt/stage/bin/ebiznes-scala-and-play", "-java-home", "/home/lwolski/.sdkman/candidates/java/current"]
