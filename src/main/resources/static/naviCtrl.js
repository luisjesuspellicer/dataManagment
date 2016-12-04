'use strict';

// Declare app level module which depends on views, and components
angular.module('frontEndApp.nav', ['ngRoute','frontEndApp.auth'])

    .controller('NavCtrl', ['auth','$route', '$location', '$window', '$http',
        function(auth,$route, $location, $window, $http) {
            var self = this;
            self.logged = auth.logged;
            var lo = function () {
                self.logged = auth.logged;
                self.admin = auth.admin;
            };
            self.admin = auth.admin;
            auth.recordObserb(lo);

            // Log out of aplication
            self.logout = function () {

                // Not authentica
                auth.logout();
                $location.path("/login");
            };
            self.control = function () {

                if(auth.isAdmin()){
                    $window.location.href = '/#/control';
                }else{
                    $window.location.href = '/#/controlUsuario';
                }
            };
            self.isAdmin = function () {
               return auth.isAdmin()
                   return true;

            }
        }]);

