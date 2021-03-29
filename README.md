# pixel_task
* [Technological Stack](#technological-stack)
* [Endpoints](#endpoints)

## Technological Stack
### Back-End
* Java (11)
* Spring-Boot
* Spring-Data-JPA
  - Hibernate
* Spring-Web
* ModelMapper
* OpenCsv

#### Tests
* JUnit
* Mockito
* Spring RestDocs

### Data Base
* H2

### Build Tools
* Maven

## Endpoints

[Full documentation](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints)

Patient -> 
``` 
/patient/visit?cities=[list of city names]&specialities=[specialization name list]
```
[Detailed documentation - Patient GET](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/patient-visits)

[Detailed documentation - Patient GET empty list](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/get-empty-list)

[Detailed documentation - Patient GET with no param](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/get-a-list-with-no-request-parameters)

[Detailed documentation - Patient GET not valid parameters](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/unknown-city-or-specialization)

Visit ->
```
/visit/number?specialization=[one specialization]
```
[Detailed documentation - Patient GET](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/visit-count-by-specialization)

[Detailed documentation - Patient GET exception](https://github.com/Kamil-java/pixel_task/tree/master/documentation/endpoints/visit-count-exception)
