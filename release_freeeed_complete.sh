#!/usr/bin/env bash
if [ -z "${ZIP_PASS}" ]; then echo Zip password not set; exit; fi

export PROJECT_DIR=$HOME/projects/SHMsoft
export FREEEED_PROJECT=$PROJECT_DIR/FreeEed
export FREEEED_REVIEW_PROJECT=$PROJECT_DIR/FreeEedReview

#============================ user setup ==================================

export UPLOAD_TO_S3_FREEEED_PLAYER=yes
export UPLOAD_TO_S3_FREEEED_REVIEW=yes
export UPLOAD_TO_S3_FREEEED_PACK=yes
export BUILD_FREEEED_PLAYER=yes
export BUILD_FREEEED_REVIEW=yes
export BUILD_FREEEED_PACK=yes

export VERSION=7.7.4

rm -rf $VERSION
mkdir $VERSION
cd $VERSION

export CURR_DIR=`pwd`
echo "Working dir: $CURR_DIR"

if [ "${BUILD_FREEEED_PLAYER}" ]; then

    echo "FreeEed: GIT pull"
    cd $FREEEED_PROJECT;git pull;
    
    echo "FreeEed: mvn clean install"
    cd $FREEEED_PROJECT/freeeed-processing;mvn clean install;
    
    echo "FreeEed: mvn package assembly:single"
    cd $FREEEED_PROJECT/freeeed-ui;mvn package assembly:single;
    
    cd $CURR_DIR
    mkdir tmp;
    cd tmp;
    
    echo "FreeEed: Copying resources to: $CURR_DIR/tmp"
    cp -R $FREEEED_PROJECT/freeeed-processing FreeEed
    cp -R $FREEEED_PROJECT/test-data .
    cd FreeEed

    cp src/main/resources/log4j.properties config/

    chmod +x prepare-clean-for-release.sh        
    
    echo "FreeEed: cleaning up...."
    ./prepare-clean-for-release.sh        

    cp settings-template.properties settings.properties
    sed -i '/download-link/d' settings.properties
    echo "download-link=http://shmsoft.s3.amazonaws.com/releases/FreeEed-$VERSION.zip" >> settings.properties
    dos2unix config/hadoop-env.sh

    cd $CURR_DIR/tmp
    
    echo "FreeEed: Creating zip file"
    zip -P $ZIP_PASS -r FreeEed-$VERSION.zip FreeEed
    cd $CURR_DIR
    mv tmp/FreeEed-$VERSION.zip .
    
    echo "FreeEed: Done -- `ls -la FreeEed-*.zip`"
fi

if [ "${BUILD_FREEEED_UI}" ]; then
    echo "FreeEed REVIEW: GIT pull"
    cd $FREEEED_REVIEW_PROJECT;git pull;
    sed -i "s/version: [0-9].[0-9].[0-9]/version: $VERSION/" $FREEEED_REVIEW_PROJECT/src/main/webapp/template/header.jsp
    cd $CURR_DIR
    cp -R $FREEEED_REVIEW_PROJECT FreeEedReview
    
    echo "FreeEed REVIEW: creating war file"
    cd FreeEedReview;mvn clean install war:war
    
    cd $CURR_DIR
    cp FreeEedReview/target/freeeedreview*.war .
    mv freeeedreview*.war freeeedreview-$VERSION.war
    rm -rf FreeEedReview
    
    echo "FreeEed Review: Done -- `ls -la freeeedreview*.war`"
fi

if [ "${BUILD_FREEEED_PACK}" ]; then
    cd $CURR_DIR/tmp
    
    echo "Downloading tomcat..."
    wget https://s3.amazonaws.com/shmsoft/release-artifacts/freeeed-tomcat.zip
    
    echo "Unzipping tomcat..."
    unzip freeeed-tomcat.zip
    rm freeeed-tomcat.zip
    mv apache-tomcat* freeeed-tomcat
    cp ../freeeedreview-$VERSION.war freeeed-tomcat/webapps/freeeedreview.war
    
    echo "Downloading Elastic search... "
    wget https://s3.amazonaws.com/freeeed-elasticsearch/elasticsearch-6.2.2.zip

    echo "Unzipping elastic search... "
    unzip elasticsearch-6.2.2.zip
    rm elasticsearch-6.2.2.zip

    cp $FREEEED_PROJECT/start_all.bat .
    cp $FREEEED_PROJECT/start_all.sh .
    
    cd $CURR_DIR
    mv tmp freeeed_complete_pack
    zip -P $ZIP_PASS -r freeeed_complete_pack-$VERSION.zip freeeed_complete_pack
    
    echo "Done -- `ls -la freeeed_complete*.zip`"
fi

if [ "${UPLOAD_TO_S3_FREEEED_PLAYER}" ]; then
    echo "Uploading $VERSION/FreeEed-$VERSION.zip to s3://shmsoft/releases/"
    echo "CURR_DIR=" $CURR_DIR
    cd $CURR_DIR
    s3cmd -P put FreeEed-$VERSION.zip s3://shmsoft/releases/
fi

if [ "${UPLOAD_TO_S3_FREEEED_UI}" ]; then
    echo "Uploading to S3.... freeeedreview-$VERSION.war"
    cd $CURR_DIR
    s3cmd -P put freeeedreview-$VERSION.war s3://shmsoft/releases/
fi

if [ "${UPLOAD_TO_S3_FREEEED_PACK}" ]; then
    echo "Uploading to S3.... freeeed_complete_pack-$VERSION.zip"
    cd $CURR_DIR
    s3cmd -P put freeeed_complete_pack-$VERSION.zip s3://shmsoft/releases/
fi

echo "Upload Done!"




