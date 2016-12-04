'use strict';

angular.module('frontEndApp.transporteSoportes', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/transporteSoportes', {
            templateUrl: 'transporteSoportes/transporteSoportes.html',
            controller: 'TransporteSoportesCtrl',
            controllerAs: 'transporteSoportesCtrl'
        });
    }])

    .controller('TransporteSoportesCtrl', ['auth', '$route', '$location', '$window', '$http', '$cookies',
        function (auth, $route, $location, $window, $http, $cookies) {
            var self = this;
            self.users = undefined;
            self.diferentPass = false;
            self.weakPass = false;
            self.notAvailable = false;


            if (auth.isAdmin() == false) {
                $location.path("/login");
            }

            $http.get('/supportES', {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }
            }).error(function (data, status, headers, config) {

                console.log(status);

            }).then(function (data) {

                self.supportsES = data.data;
                for (var i = 0; i < self.supportsES.length; i++) {

                    var date = new Date(self.supportsES[i].date);
                    var formatDate = date.toDateString();

                    self.supportsES[i].date = formatDate;
                }
            });

            $http.get('/support', {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }
            }).error(function (data, status, headers, config) {

                console.log(status);

            }).then(function (data) {

                self.supports = data.data;

            });
            self.esta = function () {

                var aux = true;

                if (self.supports.length == 0) {

                    return false;
                } else{
                    for (var i = 0; i < self.supports.length; i++) {
                        if (self.supports[i].name == self.nombree) {
                            aux = false;
                            i = self.supports.length + 3;
                        }
                    }
                    return aux;
                }
            };
            self.delete = function (support) {
                $http.delete("/supportES/id/" + support.id, {}, {
                    "Content-Type": "application/json",
                    "Authorization": 'Basic ' + $cookies.get("Basic")
                }).error(function (data, status, headers, config) {
                    console.log("error")
                }).then(function (data) {
                    $route.reload();
                });


            }

            self.crearSoporteES = function () {
                // Comprobar validez de las contraseñas.
                self.us = {
                    "support": {"name": self.nombree},
                    "emisor": self.emisor,
                    "receiver": self.receptor,
                    "typeInformation": self.tipoInformacion,
                    "shippingWay": self.formaDeEnvio,
                    "deliveryResponsible": self.responsableEmision,
                    "receivingResponsible": self.responsableRecepcion

                }
                $http.post("/supportES", self.us, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    self.nombree = "";
                    self.emisor = "";
                    self.tipoInformacion = "";
                    self.formaDeEnvio = "";
                    self.responsableEmision = "";
                    self.responsableRecepcion = "";
                    $route.reload();

                });
            }

        }]);
