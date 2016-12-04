'use strict';

angular.module('frontEndApp.soportes', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/soportes', {
    templateUrl: 'soportes/soportes.html',
    controller: 'SoportesCtrl',
    controllerAs: 'soportesCtrl'
  });
}])


.controller('SoportesCtrl', ['auth','$route', '$location', '$window', '$http','$cookies',
  function(auth,$route, $location, $window, $http, $cookies) {
    var self = this;
     self.nombree = "";
     self.tipoInformacion = "";
     self.caracteristicas = "";
  if(auth.isAdmin() == false){
    $location.path("/login");
  }

    $http.get('/support',{
      headers: {
        "Authorization": 'Basic '+ $cookies.get("Basic")}
    }).error(function(data, status, headers, config) {

      console.log(status);

    }).then(function (data) {

      self.supports = data.data;



    });

  self.delete = function (support){
    console.log(support);
    $http.delete("/support/id/" + support.id,{},{
      "Content-Type":"application/json",
      "Authorization": 'Basic '+ $cookies.get("Basic")
    }).error(function(data, status, headers, config) {
      console.log("error")
    }).then(function (data) {
      $route.reload();
    });

  }
    self.esta = function () {
      if(self.nombree == undefined || self.nombree.length == 0
      ||self.tipoInformacion == undefined || self.tipoInformacion.length == 0
      || self.caracteristicas == undefined || self.caracteristicas.length == 0){
        return true;
      }
      console.log(self.nombree);
      var aux = false;
      if (self.supports.length == 0 ) {
        return false;
      } else {
        for (var i = 0; i < self.supports.length; i++) {
          if (self.supports[i].name == self.nombree) {
            aux = true;
            i = self.supports.length + 3;
          }
        }
        return aux;
      }
    }
  self.crearSoporte = function () {
    // Comprobar validez de las contraseñas.
        if(self.esta(self.nombree)) {

        }
        else {
              self.us = {
                "name": self.nombree,
                "typeInformation": self.tipoInformacion,
                "characteristics" : self.caracteristicas
              };
              $http.post("/support", self.us, {
                "Content-Type": "application/json"
              }).error(function (data, status, headers, config) {


              }).then(function (data) {
                self.userName = "";
                self.password = "";
                self.password2 = "";
                self.email = "";

                $route.reload();

              });

        }

  }

}]);
