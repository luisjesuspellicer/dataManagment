'use strict';

angular.module('frontEndApp.incidencias', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/incidencias', {
    templateUrl: 'incidencias/incidencias.html',
    controller: 'IncidenciasCtrl',
    controllerAs: 'incidenciasCtrl'
  });
}])


.controller('IncidenciasCtrl', ['auth','$route', '$location', '$window', '$http','$cookies',
  function(auth,$route, $location, $window, $http, $cookies) {
    var self = this;

  if(auth.isAdmin() == false){
    $location.path("/login");
  }

    $http.get('/incidence',{
      headers: {
        "Authorization": 'Basic '+ $cookies.get("Basic")}
    }).error(function(data, status, headers, config) {

      console.log(status);

    }).then(function (data) {

      self.incidences = data.data;
      for(var i = 0;i<self.incidences.length; i++){

        var date = new Date(self.incidences[i].date);
        var formatDate = date.toDateString();

        self.incidences[i].date = formatDate;
      }
    });

  self.delete = function (incidence){
    console.log(incidence);
    $http.delete("/incidence/" + incidence.id,{},{
      "Content-Type":"application/json",
      "Authorization": 'Basic '+ $cookies.get("Basic")
    }).error(function(data, status, headers, config) {
      console.log("error")
    }).then(function (data) {
      $route.reload();
    });


  }

  self.crearIncidencia = function () {

            self.us = {

                "typeIncidence":self.tipoIncidencia,
                "efectsAndProcedures":self.efectos
            }
            $http.post("/incidence",self.us,  {
              headers: {
                "Authorization": 'Basic ' + $cookies.get("Basic"),
                "Content-Type":"application/json"
            }}).error(function(data, status, headers, config) {


            }).then(function (data) {
              self.tipoIncidencia = ""
              self.efectos = ""

              $route.reload();

            });
  }

}]);
