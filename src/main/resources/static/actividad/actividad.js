'use strict';

angular.module('frontEndApp.actividad', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/actividad', {
            templateUrl: 'actividad/actividad.html',
            controller: 'ActividadCtrl',
            controllerAs: 'actividadCtrl'
        });
    }])

    .controller('ActividadCtrl', ['$cookies', '$route', '$location',
        '$window', '$http', '$routeParams', 'auth',
        function ($cookies, $route, $location, $window, $http, $routeParams, auth) {
            var self = this;
            if ($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == ""
                || !auth.isAdmin()) {
                $location.path("/login");
            }
            self.actividades = [];

            self.esta = function () {
                var aux = false;
                if(self.descripcion == undefined || self.descripcion.length <= 0
                || self.tipoActividad == undefined || self.tipoActividad.length <= 0){
                    return true;
                }
                var secuencia = self.tipoActividad;

                if (self.actividades.length == 0 || secuencia == undefined
                    || secuencia.length <= 0
                ) {
                    return false;
                } else {
                    for (var i = 0; i < self.actividades.length; i++) {
                        if (self.actividades[i].typeActivity == secuencia) {
                            aux = true;
                            i = self.actividades.length + 3;
                        }
                    }
                    return aux;
                }
            }
            self.deletee = function (serie) {
                for (var i = 0; i < self.actividades.length; i++) {
                    if (self.actividades[i].typeActivity == serie.typeActivity) {
                        self.drop(self.actividades[i].typeActivity);
                        self.actividades.splice(i, 1);
                        i = self.actividades.length + 3;
                    }
                }
            }
            self.drop = function (tipoActividad) {
                $http.delete('/activity/'+ tipoActividad, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {

                });
            }
            self.pedirActividades= function () {
                $http.get('/activity', {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic")
                    }
                }).error(function (data, status, headers, config) {

                    console.log(status);

                }).then(function (data) {
                    self.actividades = data.data;

                });
            }
            self.crearActividad = function () {
                var aux = {"typeActivity": self.tipoActividad,"description":self.descripcion}
                $http.post("/activity", aux, {
                    headers: {
                        "Authorization": 'Basic ' + $cookies.get("Basic"),
                        "Content-Type": "application/json"
                    }
                }).error(function (data, status, headers, config) {


                }).then(function (data) {
                    self.pedirActividades();

                });
            }
            self.menu = function () {
                $location.path("/control");
            }
            self.pedirActividades();
        }]);
