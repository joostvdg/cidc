FROM java:8

LABEL authors="Joost van der Griendt <joostvdg@gmail.com>"
LABEL version="0.1.0"
LABEL description="OpenDJ container"

WORKDIR /opt
EXPOSE 1389 1636 4444

ENV CHANGE_DATE='20180202-1114'
ENV TINI_VERSION v0.16.1 
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
ENV OPENDJ_JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini

ENTRYPOINT ["/tini", "-v","-g", "--"]
CMD ["/opt/opendj/bin/start-ds", "--nodetach"]

RUN wget -nc -P /var/tmp/  https://github.com/ForgeRock/opendj-community-edition/releases/download/ce%2F2.6.4/OpenDJ-2.6.4.zip 
RUN unzip /var/tmp/OpenDJ-2.6.4.zip  -d /opt/ && rm -fr /var/tmp/OpenDJ-2.6.4.zip  


ADD Example.ldif /var/tmp/example.ldiff
RUN /opt/opendj/setup --cli \
    -p 1389 \
    --ldapsPort 1636 \
    --enableStartTLS \
    --generateSelfSignedCertificate \
    --baseDN dc=example,dc=com \
    -h localhost \
    --rootUserPassword password \
    --acceptLicense \
    --no-prompt \
    --doNotStart

RUN /opt/opendj/bin/import-ldif --includeBranch dc=example,dc=com --backendID userRoot --replaceExisting --ldifFile /var/tmp/example.ldiff

ADD https://github.com/krallin/tini/releases/download/${TINI_VERSION}/tini /tini
RUN chmod +x /tini
