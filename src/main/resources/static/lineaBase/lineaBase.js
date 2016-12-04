'use strict';

angular.module('frontEndApp.lineaBase', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/lineaBase', {
            templateUrl: 'lineaBase/lineaBase.html',
            controller: 'LineaBaseCtrl',
            controllerAs: 'lineaBaseCtrl'
        });
    }])

    .controller('LineaBaseCtrl', ['$cookies', '$route', '$location',
        '$window', '$http', '$routeParams', 'auth',
        function ($cookies, $route, $location, $window, $http, $routeParams, auth) {
            var self = this;
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == "") {
                $location.path("/login");
            }

            // Parámetros del controlador
            self.numSubject = $routeParams.numSubject;
            self.studyName = $routeParams.studyName;

            // Variables del formulario.
            self.numSujetos = false;
            self.noSujetos = false;
            self.numSuj = 1;
            self.sujetosListos = false;
            self.hitos = [];
            self.horaInicial = 0;
            self.horaFinal = 0;
            self.second = true;
            self.milestones = [];

            // Obtiene la información de los 2 sujetos escogidos por el usuario.
            self.segundoSujeto = function (sujeto) {
                self.second = false;
                $http.get('/subject/' + self.numSubject, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {
                    self.sujetos = [];
                    console.log(self.numSuj);
                    console.log(data.data)
                    if (self.numSuj == "2") {
                        self.sujetos.push(data.data);
                        self.sujetos.push(sujeto);
                    } else {
                        self.sujetos.push(data.data);
                    }
                });
            }

            // Indica si el usuario ha realizado la elección del numero de sujetos.
            self.eleccion = function () {
                self.numSujetos = true;
                if(self.numSuj == "1"){
                    self.second = false;
                }

            }

            // Obtiene la información de los 2 sujetos escogidos por el usuario.
            self.confirmar = function () {
                self.sujetosListos = true;
                $http.get('/subject/' + self.numSubject, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                    if (self.numSuj == "1") {
                        self.sujetos = [];
                        self.sujetoElegido = data.data;
                        self.noSujetos = true;
                    }

                });

            }

            // Obtener todos los sujetos del estudio.
            $http.get('/study/subjects/' + self.studyName, {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }
            }).error(function (data, status, headers, config) {

                console.log(status);

            }).then(function (data) {
                self.sujetos = data.data;
            });

            // Metodos para mostrar las distintas partes del formulario.
            self.mostrar2 = function () {
                return self.numSujetos && !self.sujetosListos;
            }
            self.mostrar3 = function () {
                return self.numSujetos && self.sujetosListos;
            }
            self.mostrar4 = function () {
                return self.numSujetos && !self.sujetosListos && self.numSuj == 2;
            }
            // Segundo sujeto escogido por el usuario
            self.nuevoSujeto = function (sujeto) {
                self.numSubject2 = sujeto;
            }


            // Obtener todos los posibles tipos de actividades.
            $http.get('/activity', {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }
            }).error(function (data, status, headers, config) {

                console.log(status);

            }).then(function (data) {
                self.actividades = data.data;

            });

            // Actividad elegida por el usuario.
            self.elegirActividad = function (actividad) {
                self.tipoActividad = actividad;
            }

            // Sujeto elegido por el usuario para la actividad.
            self.elegirSujeto = function (sujeto) {
                self.sujetoElegido = sujeto;
            }

            // Eliminar una actividad referenciada por el usuario
            self.deletee = function (indice) {

                self.milestones.splice(indice, 1);


            }

            // Añadir una actividad realizada.
            self.add = function () {
                var milestone = {
                    "incidents": self.incidencia,
                    "initialHour": self.horaInicial,
                    "initialMinute": self.minutoInicial,
                    "initialSecond": self.segundoInicial,
                    "initialMillisecond": self.miliSegundoInicial,
                    "finalHour": self.horaFinal,
                    "finalMinute": self.minutoFinal,
                    "finalSecond": self.segundoFinal,
                    "finalMillisecond": self.miliSegundoFinal,
                    "activities": self.tipoActividad,
                    "numSubject": self.sujetoElegido
                };
                self.milestones.push(milestone);
                $window.scrollTo(0, 0);
            }

            // Tamaño minimo para poder enviar el formulario
            self.tam = function () {
                if (self.milestones.length > 2) {
                    return false;
                } else {
                    return true;
                }
            }
            self.enviar = function () {
                var aux;
                if (self.numSuj == "1") {
                    aux = {"numSubjectOne": self.sujetoElegido,
                        "anotaciones": self.anot}
                } else {
                    aux = {

                            "numSubjectOne": self.sujetos[0],
                            "numSubjectTwo": self.sujetos[1],
                            "anotaciones": self.anot

                    }
                }
                var envio = {"baseLine": aux,"milestones": self.milestones};
                $http.post("/baseLine", envio, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    $location.path("/control");

                });
            }
        }]);
