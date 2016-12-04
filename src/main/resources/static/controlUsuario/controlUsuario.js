'use strict';

angular.module('frontEndApp.controlUsuario', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/controlUsuario', {
    templateUrl: 'controlUsuario/controlUsuario.html',
    controller: 'ControlUsuarioCtrl',
    controllerAs: 'controlUsuarioCtrl'
  });
}])

.controller('ControlUsuarioCtrl', ['auth','$route', '$location', '$window', '$http','$cookies', '$base64',
  function(auth,$route, $location, $window, $http, $cookies,$base64) {
  var self = this;
    if($cookies.get("Basic") == undefined
        || $cookies.get("Basic") == null
        || $cookies.get("Basic") == ""){
      $location.path("/login");
    }
  if(auth.isAdmin() == true){
    $location.path("/control");
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
  self.user = function (){

    self.token = $base64.decode($cookies.get("Basic"));
    console.log(self.token);
    self.nombre = self.token.split(":");
    $window.location.href = '/#/usuario?nombre='
        + self.nombre[0];

  }


}]);
