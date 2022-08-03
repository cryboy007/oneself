build_date=`date +%m%d%H`
echo "build_date 月日=${build_date}"
project_tag="${brancher}_${build_date}"
echo "project_tag=${brancher}"
docker_imageinfo= "${CONTAINER}:${project_tag}"
echo "docker_imageinfo=${docker_imageinfo}"
#强制停止
#docker stop `docker ps -a | grep -w ${CONTAINER} | awk '{print $1}'`
# 删除滚动更新残留的容器
#docker rm `docker ps -a | grep -w ${CONTAINER} | awk '{print $1}'`
# 强制删除滚动更新残留的镜像
#docker rmi --force `docker images | grep -w ${CONTAINER} | awk '{print $3}'`

# 创建新镜像
docker build -t ${docker_imageinfo} .

docker run -d -p 30001:30001 --name=${CONTAINER} ${docker_imageinfo}