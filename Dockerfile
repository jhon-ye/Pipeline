FROM harbor.hgj.net/library/yunlsp-openjdk8:latestRUN echo "http://mirrors.aliyun.com/alpine/v3.6/main" > /etc/apk/repositories \    && echo "http://mirrors.aliyun.com/alpine/v3.6/community" >> /etc/apk/repositories \    && apk update upgrade \    && apk add --no-cache procps unzip curl bash tzdata tcpdump \    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \    && echo "Asia/Shanghai" > /etc/timezoneWORKDIR /pipeline-master-appCOPY  pipeline-project/pipeline-master-app/target/pipeline-master-app.jar  /pipeline-master-app/pipeline-master-app-ark-biz.jarCMD ["/bin/bash","-c","set -e && java -jar /pipeline-master-app/pipeline-master-app-ark-biz.jar"]