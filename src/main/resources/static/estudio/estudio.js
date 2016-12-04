'use strict';

angular.module('frontEndApp.estudio', ['ngRoute','chart.js','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/estudio', {
    templateUrl: 'estudio/estudio.html',
    controller: 'EstudioCtrl',
    controllerAs: 'estudioCtrl'
  });
}])

.controller('EstudioCtrl', ['$route', '$location', '$window', '$http','auth','$cookies',
  function($route, $location, $window, $http, auth, $cookies) {
    var self = this;
    self.mostrarEstudio = 0;
    if($cookies.get("Basic") == undefined
    || $cookies.get("Basic") == null
    || $cookies.get("Basic") == ""){
      $location.path("/login");
    }
    self.roles = auth.getRoles();
    if(self.roles == undefined){
      $location.path("/login");
    }
    self.esta = function () {
      var aux = false;
      if(self.nombreEstudio == undefined || self.descripcion == undefined
           || self.descripcion.length <= 0 || self.nombreEstudio.length <= 0){
        return true;

      } else {
        for (var i = 0; i < self.estudios.length; i++) {
          if (self.estudios[i].studyName == self.nombreEstudio) {
            aux = true;
            i = self.estudios.length + 3;
          }
        }
        return aux;
      }
    }

    self.len = function () {
      if(self.estudios == undefined || self.estudios.length == 0){
        return false;
      }else{return true;}
    }
    //console.log($cookies.get("Basic"));
    self.crearNuevoEstudio = function () {
      self.mostrarEstudio = 1;
    }
    self.pedirEstudios = function () {
      if(self.roles[0] == "ROLE_ADMIN"){
        $http.get('/study',{
          headers: {
            "Authorization": 'Basic '+ $cookies.get("Basic")}
        }).error(function(data, status, headers, config) {

          console.log(status);

        }).then(function (data) {

          self.estudios = data.data;
        });
      }else{

        // No hace falta pedir estudios
        self.estudios = new Array();
        var a = undefined;
        if(self.roles != undefined && self.roles != ""){
          for (var i = 0; i < self.roles.length; i++) {
            a = self.roles[i].split("_")
            self.estudios[i] = {"studyName": a[1]}

          }
        }

      }

    }
    self.crear = function () {
      self.nuevoEstudio = {
        'studyName':self.nombreEstudio,
        'description':self.descripcion
      }
      $http.post('/study', self.nuevoEstudio, {
        headers: {
          "Authorization": 'Basic ' + $cookies.get("Basic")
        }
      }).error(function(data, status, headers, config) {

          console.log(status);
      }).then(function (data) {
        self.pedirEstudios();
        self.mostrarEstudio = 0;

      });

    }
    self.open = function (indice) {

        $window.location.href = '/#/controlEstudio?'+'nombre='+indice.studyName;

    }
    self.administrador = function () {
      if(auth.isAdmin()){
        return true;
      }else{
        return false;
      }
    }
    self.deletee = function (estudio) {
      $http.delete("/study/" + estudio.studyName, {
        headers: {
          "Authorization": 'Basic ' + $cookies.get("Basic")
        }
      }).error(function (data, status, headers, config) {
        console.log("error")
      }).then(function (data) {
        $route.reload();
      });
    }
    self.pedirEstudios();

}]);
