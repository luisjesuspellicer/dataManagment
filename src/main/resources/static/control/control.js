'use strict';

angular.module('frontEndApp.control', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/control', {
    templateUrl: 'control/control.html',
    controller: 'ControlCtrl',
    controllerAs: 'controlCtrl'
  });
}])

.controller('ControlCtrl', ['auth','$route', '$location', '$window', '$http','$cookies',
  function(auth,$route, $location, $window, $http, $cookies) {
  var self = this;
  if(auth.isAdmin() == false){
    $location.path("/controlUsuario");
  }
  self.cuentas = function () {
    $location.path("/cuentas");
  }

  self.aepd = function () {
    $location.path("/aepd");
  }

  self.estudio = function () {
    $location.path("/estudio");
  }

  self.testCognitivo = function(){
    $location.path("/testCognitivo");
  }

  self.actividad = function () {
    $location.path("/actividad");
  }
}]);
