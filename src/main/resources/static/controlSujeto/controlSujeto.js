'use strict';

angular.module('frontEndApp.controlSujeto', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/controlSujeto', {
            templateUrl: 'controlSujeto/controlSujeto.html',
            controller: 'ControlSujetoCtrl',
            controllerAs: 'controlSujetoCtrl'
        });
    }])

    .controller('ControlSujetoCtrl', ['$cookies', '$route', '$location',
        '$window', '$http', '$routeParams', 'auth', '$sce','spinnerService',
        function ($cookies, $route, $location, $window, $http, $routeParams, auth, $sce,spinnerService) {
            var self = this;
            self.t = "";
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == "") {
                $location.path("/login");
            }

            /*
             *Parametros del controlador para poder mostrar distintas partes
             * a elección del usuario.
             */
            self.download2 = true;
            self.descar = false;
            self.fich = "Mostrar Ficheros";
            self.numSujeto = $routeParams.numSubject;
            self.s = '/subject/fileInfo/' + self.numSujeto;
            self.series = [];
            self.actividades = [];
            self.testsCognitivos = [];
            self.testsResueltos = [];
            self.studyName1 = $routeParams.studyName;
            self.ficheros = [];
             // Pedir personal code
            $http.get('/subject/' + self.numSujeto, {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }
            }).error(function (data, status, headers, config) {

                console.log(status);

            }).then(function (data) {

                self.personalCode = data.data.personalCode;
            });
            self.deletee = function (serie) {
                for (var i = 0; i < self.series.length; i++) {
                    if (self.series[i].secuencia == serie.secuencia) {
                        self.series.splice(i, 1);
                        i = self.series.length + 3;
                    }
                }
            }

            self.redirigirTest = function (tes) {
                $window.location.href = '/#/testCognitivoResultado?'
                    + 'numSubject=' + self.numSujeto + "&test=" + self.tests[tes].test_id;
            }


            self.pedirTest = function () {

                $http.get('/cognitiveTest', {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                    self.tests = data.data;
                });

            }

            // Comprobar si el usuario es administrador.
            self.canDelete = function () {
                return auth.isAdmin();
            }

            // Descargar un fichero.
            self.download = function (fichero) {
                self.download2 = false;
                self.descar = true;
                $window.open('/fileInfo?file=' + fichero.nameFile);

            }

            // Mostrar opciones para introducir y descargar ficheros.
            self.mostrarFichero = function () {
                if (self.mostrar == 0) {
                    self.mostrar = 3;
                    self.fich = "Ocultar ficheros";
                } else if (self.mostrar == 3) {
                    self.mostrar = 0;
                    self.fich = "Mostrar ficheros"
                }
            }

            self.pedirTestsResultados = function () {
                $http.get('/subject/cognitiveTest/' + self.numSujeto, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                    self.testsResueltos = data.data.solutions;
                    self.testsCognitivos = data.data.tests;
                });
            }
            self.spin = function () {

            }
            // Comprobar si el sujeto ha respondido a algún test.
            self.noHayTests = function () {
                if (self.testsCognitivos.length > 0) {
                    return false;
                } else {
                    return true;
                }

            }

            // Comprobar si el sujeto ha realizado alguna actividad.
            self.noHayActividades = function () {
                if (self.actividades.length > 0) {
                    return false;
                } else {
                    return true;
                }
            }

            self.pedirActividades = function () {
                $http.get('/subject/baseLine/' + self.numSujeto, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                    self.actividades = data.data;
                });
            }


            self.pedirMilestones = function (actividad) {
                $http.get('/baseLine/milestones/' + actividad.id, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {
                    console.log(status);

                }).then(function (data) {

                    self.milestones = data.data;
                });
            }
            self.mostrar = 0;

            // Mostrar las actividades (lineas base) que el sujeto ha realizado.
            self.mostrarActividad = function (actividad) {
                self.pedirMilestones(actividad);
                self.mostrar = 2;
            }

            // Incluir las actividades (lineas base) que el sujeto ha realizado.
            self.pedirActividades();

            /**
             * Mostrar los resultados de los tests de los sujetos.
             * @param test
             */
            self.mostrarTest = function (test) {

                // Test es el testResolution. Buscar las series del testCognitivo y
                // las respuestas.+

                $http.get('cognitiveTest/' + test.test_id.test_id, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                    self.series = data.data;

                    $http.get('cognitiveTestResolution/' + test.test_id_resolution.id, {
                        headers: {
                            "Authorization": 'Basic ' + $cookies.get("Basic")
                        }
                    }).error(function (data, status, headers, config) {

                        console.log(status);

                    }).then(function (data) {

                        self.solutions = data.data;
                    });
                });

                self.mostrar = 1;
            }

            /**
             * Ocultar los resultados de los tests.
             */
            self.noMostrarTest = function () {
                self.mostrar = 0;

            }

            self.lineaBase = function () {
                $window.location.href = '/#/lineaBase?'
                    + 'numSubject=' + self.numSujeto + '&studyName=' + self.studyName1;
            }
            self.pedirTest();
            self.pedirTestsResultados();

            self.pedirFicheros = function () {
                $http.get('subject/fileInfo/' + self.numSujeto, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);
                    alert("No se han podido recuperar los ficheros asociados a este sujeto");

                }).then(function (data) {

                    self.ficheros = data.data;
                });
            }

            self.pedirFicheros();

            self.deleteFichero = function (fichero) {
                $http.delete('/fileInfo', {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    },
                    params: {
                        "file": fichero.nameFile

                    }
                }).error(function (data, status, headers, config) {

                    alert("No se ha podido eliminar el fichero");
                    console.log(status);

                }).then(function (data) {
                    self.pedirFicheros();
                });
            }
        }]);
