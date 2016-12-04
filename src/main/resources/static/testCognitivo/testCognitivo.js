'use strict';

angular.module('frontEndApp.testCognitivo', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/testCognitivo', {
            templateUrl: 'testCognitivo/testCognitivo.html',
            controller: 'TestCognitivoCtrl',
            controllerAs: 'testCognitivoCtrl'
        });
    }])

    .controller('TestCognitivoCtrl', ['$cookies', '$route', '$location',
        '$window', '$http', '$routeParams', 'auth',
        function ($cookies, $route, $location, $window, $http, $routeParams, auth) {
            var self = this;
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == ""
                || !auth.isAdmin()) {
                $location.path("/login");
            }
            self.series = [];
            self.nuevaSerie = function () {
                var auxi = {
                    "secuencia": self.sequence, "numero": self.contarNumeros(self.sequence),
                    "letras": self.contarLetras(self.sequence)
                }
                self.series.push(auxi);
                self.sequence = undefined;
            }
            self.contarLetras = function (text) {
                var auxLetras = "abcdefghijklmnñopqrstuvwxyz" +
                    "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
                var aux = 0;
                for (var i = 0; i < text.length; i++) {
                    if (auxLetras.indexOf(text.charAt(i), 0) != -1) {
                        aux = aux + 1;
                    }
                }
                return aux;
            }
            self.contarNumeros = function (text) {
                var auxNumeros = "1234567890";
                var aux = 0;
                for (var i = 0; i < text.length; i++) {
                    if (auxNumeros.indexOf(text.charAt(i), 0) != -1) {
                        aux = aux + 1;
                    }
                }
                return aux;
            }
            self.alfanumerico = function () {
                if (self.sequence == undefined || self.sequence.length <= 0) {
                    return true;
                } else {
                    var numLetras = self.contarLetras(self.sequence);
                    var numNum = self.contarNumeros(self.sequence);
                    if (self.sequence.length != (numLetras + numNum)) {
                        return true;
                    }
                    else {
                        if (self.esta(self.sequence)) {
                            return true;

                        } else {
                            return false;
                        }
                    }
                }
            }
            self.esta = function (secuencia) {
                var aux = false;
                if (self.series.length == 0 || secuencia == undefined
                    || secuencia.length <= 0
                ) {
                    return false;
                } else {
                    for (var i = 0; i < self.series.length; i++) {
                        if (self.series[i].secuencia == secuencia) {
                            aux = true;
                            i = self.series.length + 3;
                        }
                    }
                    return aux;
                }
            }
            self.deletee = function (serie) {
                for (var i = 0; i < self.series.length; i++) {
                    if (self.series[i].secuencia == serie.secuencia) {
                        self.series.splice(i, 1);
                        i = self.series.length + 3;
                    }
                }
            }
            self.crearTest = function () {

                var series2 = [];
                for (var i = 0; i < self.series.length; i++) {
                    series2.push({"sequence":self.series[i].secuencia,
                        "numberOfLetters": self.series[i].letras,
                        "numberOfNumbers": self.series[i].numero,
                        "total": self.series[i].letras + self.series[i].numero})
                }

                var aux = {"cognitiveTest": {"nombre": self.nombree}, "series": series2};
                $http.post("/cognitiveTest", aux, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    $location.path("/control");

                });


            }
            self.completo = function () {
                if (self.series.length != 10) {
                    return true;
                } else {
                   return false;
                }
            }
            self.nombre = function () {
                if (self.nombree == undefined ||
                    self.nombree.length == 0) {
                    return true;
                } else {
                    return false;
                }

            }
            self.menu = function () {
                $location.path("/control");
            }
        }]);
