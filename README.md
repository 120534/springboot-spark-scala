## SpringBoot-Spark-Scala  
springboot deployed with spark, writing in scala  

### example  
start the project: `mvn spring-boot:run`  
a **wordcount** Http service will starte at http://localhost:8080/wordcount  
we can use post method to send the params like below.
![image](https://github.com/120534/springboot-spark-scala/blob/master/request%26response.png)  


a **raster** service for .tiff data,the entry point at http://localhost:8080/raster  
![image](https://github.com/120534/springboot-spark-scala/blob/master/request%26rasterinfo.png)

a **ndvi** calculation service based on RasterFrame [RasterFrames](http://rasterframes.io/) at http://localhost:8080/png?name=L8-Elkton-VA  
![image](https://github.com/120534/springboot-spark-scala/blob/master/ndvi.png)
