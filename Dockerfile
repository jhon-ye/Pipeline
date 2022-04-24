# zhushi
FROM harbor.hgj.net/library/yunlsp-openjdk8:latest

WORKDIR /pipeline-master-app

COPY  pipeline-project/pipeline-master-app/target/pipeline-master-app-ark-biz.jar  /pipeline-master-app/pipeline-master-app-ark-biz.jar

CMD ["/bin/bash","-c","set -e && java -jar /pipeline-master-app/pipeline-master-app-ark-biz.jar"]
