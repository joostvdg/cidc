#############
## BUILD
FROM caladreas/mkdocs-docker-build-container:2.0.0 as build
WORKDIR /src
ENV LAST_UPDATE=20180209
ADD . /src
RUN ls -lath /src
RUN mkdocs build
RUN ls -lath /src/site
##############

##################
## RUN
FROM caladreas/apache2-openidc-docker:0.2.0
COPY --from=build /src/site /var/www/html/
##########
