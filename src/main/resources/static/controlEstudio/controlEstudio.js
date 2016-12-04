'use strict';

angular.module('frontEndApp.controlEstudio', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/controlEstudio', {
            templateUrl: 'controlEstudio/controlEstudio.html',
            controller: 'ControlEstudioCtrl',
            controllerAs: 'controlEstudioCtrl'
        });
    }])

    .controller('ControlEstudioCtrl', ['auth', '$route', '$location', '$window', '$http', '$cookies', '$routeParams',
        function (auth, $route, $location, $window, $http, $cookies, $routeParams) {
            var self = this;
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == "") {
                $location.path("/login");
            }
            self.sujetos = [];
            self.filtrados = false;
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
            self.pedirSujetos = function () {
                $http.get('/study/subjects/' + self.estudioo, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {
                    self.sujetos = data.data;
                });
            }

            self.estudioo = $routeParams.nombre;

            self.sujetoo = $routeParams.numSubject;

            self.pedirSujetos();

            self.registroo = function () {
                $window.location.href = '/#/registro?' + 'nombre=' + self.estudioo;
            }
            self.redirigirTest = function (tes) {
                $window.location.href = '/#/testCognitivoResultado?'
                    + 'numSubject=' + self.sujetoo + "&test=" + self.tests[tes].test_id;
            }
            self.convertirAFecha = function (milisegundos) {
                var a = new Date(milisegundos);
                return a;
            }
            self.sujeto = function () {
                if (self.sujetoo == undefined) {
                    return true;
                } else {
                    return false;
                }
            }
            self.canDelete = function () {
                return auth.isAdmin();
            }
            self.estudio = function () {
                if (self.estudioo == undefined) {
                    return true;
                } else {
                    return false;
                }
            }
            self.open = function (sujeto) {
                $window.location.href = '/#/controlSujeto?'
                    + 'numSubject=' + sujeto.numSubject + '&studyName=' + self.estudioo;
            }
            self.fechas = function () {
                if (self.dia == undefined || self.dia == undefined && self.diaa == undefined) {
                    return true;
                } else {
                    return false;
                }
            }
            self.noFiltrar = function () {
                self.pedirSujetos();
                self.filtrados = false;
            };
            self.filtrar = function () {
                self.filtrados = true;

                // Primero convertir fechas. con dias anteriores y posteriores
                var finDia1 = new Date(self.dia);
                var inicioDia1 = new Date(self.dia);
                var finDia2;
                if(self.diaFin != undefined){
                    finDia2 = new Date(self.diaFin);

                    finDia2.setHours(23);
                    finDia2.setMinutes(59);
                    finDia2.setSeconds(59);
                }



                inicioDia1.setHours(0);
                inicioDia1.setMinutes(0);
                inicioDia1.setSeconds(0);
                inicioDia1.setMilliseconds(0);

                finDia1.setHours(23);
                finDia1.setMinutes(59);
                finDia1.setSeconds(59);




                if (self.diaFin != undefined) {

                    for (var i = 0; i < self.sujetos.length; i++) {

                        if (self.sujetos[i].checkIn < inicioDia1.getTime()
                            || self.sujetos[i].checkIn > finDia2) {
                            self.sujetos.splice(i, 1);
                        }
                    }
                }else{
                    for (var i = 0; i < self.sujetos.length; i++) {
                        if (self.sujetos[i].checkIn < inicioDia1.getTime()
                        || self.sujetos[i].checkIn > finDia1) {
                            self.sujetos.splice(i, 1);
                        }
                    }
                }

            }
            self.deletee = function (subject) {
                $http.delete("/subject/" + subject.numSubject, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {
                    console.log("error")
                }).then(function (data) {
                    $route.reload();
                });
            }
            self.pedirTest();
        }]);
