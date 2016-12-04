'use strict';

angular.module('frontEndApp.testCognitivoResultado', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/testCognitivoResultado', {
            templateUrl: 'testCognitivoResultado/testCognitivoResultado.html',
            controller: 'TestCognitivoResultadoCtrl',
            controllerAs: 'testCognitivoResultadoCtrl'
        });
    }])

    .controller('TestCognitivoResultadoCtrl', ['$cookies', '$route', '$location',
        '$window', '$http', '$routeParams', 'auth',
        function ($cookies, $route, $location, $window, $http, $routeParams, auth) {
            var self = this;
            self.indice = 0;
            self.N = [];
            self.L = [];
            self.T = [];
            self.P =[];
            self.P2 = [];
            self.numCompletos = 0;
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == "") {
                $location.path("/login");
            }

            self.numSubject =  $routeParams.numSubject;
            self.numTest =  $routeParams.test;

            self.series = [];
            $http.get('/cognitiveTest/' + self.numTest,{
                headers: {
                    "Authorization": 'Basic '+ $cookies.get("Basic")}
            }).error(function(data, status, headers, config) {

                console.log(status);

            }).then(function (data) {

                self.series = data.data;
            });


            self.crearTest = function () {

                var series2 = [];
                for (var i = 0; i < self.series.length; i++) {
                    series2.push({"sequence":self.series[i].secuencia,
                        "numberOfLetters": self.L[i],
                        "numberOfNumbers": self.N[i],
                        "total": self.L[i] + self.N[i]})
                }
                var aux = {"cognitiveTest": {"nombre": self.nombre}, "series": series2};
                $http.post("/cognitiveTestResolution", aux, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    $location.path("/control");

                });
            }

            self.actual = function () {
                return self.series[self.indice].sequence;
            }
            self.fin = function () {
                return self.numCompletos == 10;
            }
            self.siguienteSerie = function () {
                self.T[self.indice]=self.L[self.indice] + self.N[self.indice];
                if(self.numCompletos != 10){
                    self.numCompletos = self.numCompletos + 1;
                }
                if(self.indice == 9){
                    self.indice = 0;
                }else{
                    self.indice = self.indice +1;
                }
            }
            self.enviar = function () {
                var series2 = [];
                for (var i = 0; i < self.series.length; i++) {
                    series2.push({"sequence":self.series[i].sequence,
                        "numberOfLetters": self.L[i],
                        "numberOfNumbers": self.N[i],
                        "total": self.L[i] + self.N[i],
                        "numSerie": {"numSerie":self.series[i].numSerie}})
                }

                var aux = {"cognitiveTest": {"test_id": self.numTest},
                            "subject": {"numSubject":self.numSubject},
                            "series": series2};
                $http.post("/cognitiveTestResolution", aux, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    $window.location.href = '/#/controlSujeto?numSubject='+ self.numSubject;

                });


            }
        }]);
