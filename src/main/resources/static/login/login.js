'use strict';

angular.module('frontEndApp.login', ['ngRoute', 'frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'login/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'loginCtrl'
        });
    }])

    .controller('LoginCtrl', ['$base64', '$cookies', 'auth', '$route', '$location', '$window', '$http',
        function ($base64, $cookies, auth, $route, $location, $window, $http) {
            var self = this;
            self.usuarios = true;
            self.crear = "Login";
            self.crear2 = "Crear";
            self.log = function () {

                if (self.usuarios == true) {

                    // Login normal con usuario.

                    var token = auth.generateToken(self.userName, self.password);
                    $http.post("/log", {'userName': self.userName}, {
                        headers: {
                            "Authorization": 'Basic ' + token
                        }
                    }).error(function (data, status, headers, config) {


                    }).then(function (data) {

                        self.usuarios = true;

                        console.log(data.data);
                        auth.login(data.data.userName, self.password, data.data.privileges);
                        self.password = null;
                        // wait the autentication.
                        while (auth.logged == false) {

                        }

                        $location.path("/control");


                    });
                } else {

                    // Crear nuevo administrador.
                    self.us = {
                        "userName": self.userName,
                        "password": self.password,
                        "privileges": "ROLE_ADMIN",
                        "email": self.email

                    }
                    $http.post("/admin", self.us, {
                        "Content-Type": "application/json"
                    }).error(function (data, status, headers, config) {

                    }).then(function (data) {
                        self.crear = "Login";
                        self.crear2 = "Iniciar sesión";
                        self.usuarios = true;

                        $http.post("/logout", {}, {
                            'Content-Type': 'application/json'
                        }).error(function (data, status, headers, config) {


                        }).then(function (data) {

                        });

                    });
                }

            }
            $http.get('/numUsers', {}).error(function (data, status, headers, config) {


            }).then(function (data) {
                if (data.data == 0) {
                    self.usuarios = false;
                    self.crear = "Crear cuenta de administrador";
                    self.crear2 = "Crear"
                } else {
                    self.crear = "Login";
                    self.crear2 = "Iniciar sesión";
                    if ($cookies.get("Basic") != undefined
                        && $cookies.get("Basic") != null
                        && $cookies.get("Basic") != ""
                    ) {
                        console.log($cookies.get("Basic"));
                        var token3 = $base64.decode($cookies.get("Basic"))
                        var aux4 = token3.split(":");
                        self.userName = aux4[0];
                        self.password = aux4[1];


                        self.log();
                    }
                }

            });




        }]);
