all: docker

.PHONY: all docker

docker:
	docker build --tag=bengyun/webservice -f docker/Dockerfile .

