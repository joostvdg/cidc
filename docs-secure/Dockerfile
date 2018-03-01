#############
## BUILD
FROM q-nexus-3.nl.eu.abnamro.com:18445/caladreas/mkdocs-docker-build-container:2.0.0 as build
WORKDIR /src
ENV LAST_UPDATE=20180209
ADD . /src
RUN ls -lath /src
RUN mkdocs build
RUN ls -lath /src/site
##############

##################
## RUN
FROM q-nexus-3.nl.eu.abnamro.com:18445/caladreas/apache2-openidc-docker:0.2.0
COPY --from=build /src/site /var/www/html/
##########