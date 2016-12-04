'use strict';

angular.module('frontEndApp.cuentas', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/cuentas', {
    templateUrl: 'cuentas/cuentas.html',
    controller: 'CuentasCtrl',
    controllerAs: 'cuentasCtrl'
  });
}])

.controller('CuentasCtrl', ['auth','$route', '$location', '$window', '$http','$cookies',
  function(auth,$route, $location, $window, $http, $cookies) {
    var self = this;
    self.users = undefined;
    self.diferentPass = false;
    self.weakPass = false;
    self.notAvailable = false;


  if(auth.isAdmin() == false){
    $location.path("/login");
  }

    $http.get('/user',{
      headers: {
        "Authorization": 'Basic '+ $cookies.get("Basic")}
    }).error(function(data, status, headers, config) {

      console.log(status);

    }).then(function (data) {

      self.users = data.data;



    });
  self.canDelete = function (user) {
    if( user == undefined){
      return true;
    }else{

      if(user.privileges!= undefined ){
        var aux = user.privileges.split(";")
        if(aux.indexOf("ROLE_ADMIN") != -1){
          return false;
        }
        else{
          return true;
        }
      }else{
        return true;
      }
    }
  }

  self.weak = function () {
    return self.weakPass;
  }
  self.delete = function (user){
    $http.delete("/user/" + user.userName,{},{
      "Content-Type":"application/json",
      "Authorization": 'Basic '+ $cookies.get("Basic")
    }).error(function(data, status, headers, config) {
      console.log("error")
    }).then(function (data) {
      $route.reload();
    });


  }
  self.notAvailablee = function () {
    return self.notAvailable;
  }
  self.dif = function () {
    return self.diferentPass;
  }
  self.perfil = function (user) {
    $window.location.href = '/#/usuario?'+'nombre='+user.userName;
  }
  self.crearUsuario = function () {
    // Comprobar validez de las contraseñas.

    if(self.password != self.password2){

      self.diferentPass = true;
    }else{
      self.diferentPass = false;
      if(self.password.search(/\d/) == -1){

        self.weakPass=true;
      }else if(self.password.search(/[a-z]/) == -1){

        self.weakPass=true;
      }else if(self.password.search(/[A-Z]/) == -1){

        self.weakPass=true;
      }else{
        self.weakPass = false;
        self.diferentPass = false;
        // Good Password. Check if userName is available
        $http.get('/user/'+self.userName,{
          headers: {
            "Authorization": 'Basic '+ $cookies.get("Basic")}
        }).error(function(data, status, headers, config) {
          if(status == 404){
            // Crear usuario
            self.notAvailable = false;
            self.us = {
              "userName": self.userName,
              "password": self.password,
              "privileges": "",
              "email": self.email
            }
            $http.post("/user",self.us,{
              "Content-Type":"application/json"
            }).error(function(data, status, headers, config) {


            }).then(function (data) {
              self.userName = ""
              self.password = ""
              self.password2 = "";
              self.email = "";

              $route.reload();

            });

          }// Error servidor
          else{

            console.log(status);
          }


        }).then(function (data) {

          self.notAvailable = true;

        });
      }
    }

  }

}]);
