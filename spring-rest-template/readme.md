## About this Repo 

### [Hosted here](https://spring-rest-1.herokuapp.com/swagger-ui/index.html#/)

### I have implemented the following in this repo 

- Basic CRUD Rest API  _____
- Added an in memory Db 
- Wrote Unit Tests for the same
- Added Custom Exception handling response format
- Added Locale for different Timezones

### The Open API 3 Document of this API 

``` javascript
{
   "openapi": "3.0.1",
   "info": {
      "title": "OpenAPI definition",
      "version": "v0"
   },
   "servers": [
      {
         "url": "http://localhost:8080",
         "description": "Generated server url"
      }
   ],
   "paths": {
      "/v2/users": {
         "get": {
            "tags": [
               "user-controller"
            ],
            "operationId": "retrieveAllUsersv2",
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "type": "array",
                           "items": {
                              "$ref": "#/components/schemas/User"
                           }
                        }
                     }
                  }
               }
            }
         },
         "post": {
            "tags": [
               "user-controller"
            ],
            "operationId": "createUserv2",
            "requestBody": {
               "content": {
                  "application/json": {
                     "schema": {
                        "$ref": "#/components/schemas/User"
                     }
                  }
               },
               "required": true
            },
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "type": "object"
                        }
                     }
                  }
               }
            }
         }
      },
      "/v2/users/{id}": {
         "get": {
            "tags": [
               "user-controller"
            ],
            "operationId": "retrieveUsersByIdv2",
            "parameters": [
               {
                  "name": "id",
                  "in": "path",
                  "required": true,
                  "schema": {
                     "type": "integer",
                     "format": "int32"
                  }
               }
            ],
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "$ref": "#/components/schemas/EntityModelUser"
                        }
                     }
                  }
               }
            }
         },
         "delete": {
            "tags": [
               "user-controller"
            ],
            "operationId": "deleteUsev2r",
            "parameters": [
               {
                  "name": "id",
                  "in": "path",
                  "required": true,
                  "schema": {
                     "type": "integer",
                     "format": "int32"
                  }
               }
            ],
            "responses": {
               "200": {
                  "description": "OK"
               }
            }
         }
      },
      "/v2/hello-world-internationalized": {
         "get": {
            "tags": [
               "hello-world-controller"
            ],
            "operationId": "helloWorldInternationalizedWithLocaleContextHolder",
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "type": "string"
                        }
                     }
                  }
               }
            }
         }
      },
      "/v1/hello-world-internationalized": {
         "get": {
            "tags": [
               "hello-world-controller"
            ],
            "operationId": "helloWorldInternationalizedWithLocale",
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "type": "string"
                        }
                     }
                  }
               }
            }
         }
      },
      "/hello-world": {
         "get": {
            "tags": [
               "hello-world-controller"
            ],
            "operationId": "helloWorld",
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "$ref": "#/components/schemas/HelloWorldBean"
                        }
                     }
                  }
               }
            }
         }
      },
      "/hello-world/{paramPassed}": {
         "get": {
            "tags": [
               "hello-world-controller"
            ],
            "operationId": "helloWorldPathVariable",
            "parameters": [
               {
                  "name": "paramPassed",
                  "in": "path",
                  "required": true,
                  "schema": {
                     "type": "string"
                  }
               }
            ],
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "$ref": "#/components/schemas/HelloWorldBean"
                        }
                     }
                  }
               }
            }
         }
      },
      "/": {
         "get": {
            "tags": [
               "hello-world-controller"
            ],
            "operationId": "defaultMapping",
            "responses": {
               "200": {
                  "description": "OK",
                  "content": {
                     "*/*": {
                        "schema": {
                           "type": "string"
                        }
                     }
                  }
               }
            }
         }
      }
   },
   "components": {
      "schemas": {
         "User": {
            "type": "object",
            "properties": {
               "id": {
                  "type": "integer",
                  "format": "int32"
               },
               "name": {
                  "maxLength": 2147483647,
                  "minLength": 2,
                  "type": "string"
               },
               "birthDate": {
                  "type": "string",
                  "format": "date-time"
               }
            }
         },
         "EntityModelUser": {
            "type": "object",
            "properties": {
               "id": {
                  "type": "integer",
                  "format": "int32"
               },
               "name": {
                  "maxLength": 2147483647,
                  "minLength": 2,
                  "type": "string"
               },
               "birthDate": {
                  "type": "string",
                  "format": "date-time"
               },
               "links": {
                  "type": "array",
                  "items": {
                     "$ref": "#/components/schemas/Link"
                  }
               }
            }
         },
         "Link": {
            "type": "object",
            "properties": {
               "rel": {
                  "type": "string"
               },
               "href": {
                  "type": "string"
               },
               "hreflang": {
                  "type": "string"
               },
               "media": {
                  "type": "string"
               },
               "title": {
                  "type": "string"
               },
               "type": {
                  "type": "string"
               },
               "deprecation": {
                  "type": "string"
               },
               "profile": {
                  "type": "string"
               },
               "name": {
                  "type": "string"
               }
            }
         },
         "HelloWorldBean": {
            "type": "object",
            "properties": {
               "message": {
                  "type": "string"
               }
            }
         }
      }
   }
}
```
