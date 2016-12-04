'use strict';

angular.module('frontEndApp.usuario', ['ngRoute','frontEndApp.auth'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/usuario', {
    templateUrl: 'usuario/usuario.html',
    controller: 'UsuarioCtrl',
    controllerAs: 'usuarioCtrl'
  });
}])

.controller('UsuarioCtrl', ['auth','$route', '$location', '$window', '$http','$cookies','$routeParams',
  function(auth,$route, $location, $window, $http, $cookies, $routeParams) {
    var self = this;
    self.users = undefined;
    self.permisos = false;
    self.cambios = true;
    self.camb = "modificar";
    self.roles = undefined;
    self.aux4 = undefined;
    self.aux3 = undefined;
    self.aux2 = undefined;
    self.aux1 = undefined;
    self.roles = undefined;
    self.email = undefined;



    // Si es administrador puede modificar los permisos sino no.
    if($cookies.get("Basic") == undefined
        || $cookies.get("Basic") == null
        || $cookies.get("Basic") == ""){
      $location.path("/login");
    }

    $http.get('/user/' + $routeParams.nombre, {
      headers: {
        "Authorization": 'Basic ' + $cookies.get("Basic")
      }
    }).error(function (data, status, headers, config) {

      console.log(status);

    }).then(function (data) {
      console.log(data);
      self.aux1 = data.data.userName;
      self.aux2 = data.data.email;
      self.aux3 = data.data.privileges;
      self.email = data.data.email;
      self.userName = data.data.userName;
      self.roles = data.data.privileges;
      if(auth.isAdmin() == true){
        self.pedirEstudios();
      }

    });
    self.cambiar = function () {

        return self.cambios;
    }

    self.borrarRoles = function () {
        self.roles = "";
        self.estudios = self.aux4;

        self.actualizarEstudios();

    }
    self.aplicarCambios = function () {
      if(self.cambios == false){
        self.cambios = true;
        self.roles = self.aux3;
        self.email = self.aux2
          self.camb = "Modificar"
      } else{
        self.estudios = self.aux4
        self.actualizarEstudios();
        self.userName = self.aux1;
        self.email = self.aux2;
        self.roles = self.aux3;
        self.cambios = false;
          self.camb = "No modificar"

      }
    }
  self.isNotAdmin = function () {
    if(auth.isAdmin() == false){
      return false;
    }else{
      return true;
    }
  }
  /*    self.isAdmin = function () {
          if(self.aux3 == undefined) {
              return false;
          }else{
              if(self.aux3.includes("ROLE_ADMIN")){
                  return true;
              }else{
                  return false;
              }
          }
      }*/
  self.actualizarEstudios = function () {
    $http.get('/study', {
      headers: {
        "Authorization": 'Basic ' + $cookies.get("Basic")
      }
    }).error(function (data, status, headers, config) {

      console.log(status);

    }).then(function (data) {

      self.aux4 = data.data;
      self.estudios = self.aux4;
      if(self.roles != "") {

        var auxi2 = self.roles.split(";");

        for (var j = 0; j < auxi2.length; j++) {

          for (var i = 0; i < self.estudios.length; i++) {


            var auxi = auxi2[j].split("_");

            if (auxi[1] == self.estudios[i].studyName) {

              self.estudios.splice(i, 1);
              i = self.estudios.length + 1;

            }
          }
        }
      }
    });


  }

  self.pedirEstudios = function () {

      $http.get('/study', {
          headers: {
              "Authorization": 'Basic ' + $cookies.get("Basic")
          }
      }).error(function (data, status, headers, config) {

          console.log(status);

      }).then(function (data) {

          self.aux4 = data.data;
          self.estudios = self.aux4;

      });
  }

  self.canDelete = function (user) {
    if( user == undefined){
      return true;
    }else{

      if(user.privileges!= undefined ){
        var auxi = user.privileges.split(";")
        if(auxi.indexOf("ROLE_ADMIN") != -1){
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

  self.userNotAdmin = function () {

    if(self.roles != undefined){
      if(self.roles.includes("ADMIN")){
        return false;
      }else{

        return true;
      }
    }else{

        return false;
    }


  }
  self.modificarNombre = function () {
    return false;
  }
  self.delete = function (user){
    $http.delete("/user",user,{
      "Content-Type":"application/json",
      "Authorization": 'Basic '+ $cookies.get("Basic")
    }).error(function(data, status, headers, config) {

    }).then(function (data) {
      self.crear = "Login";
      self.crear2 = "Iniciar sesión";
      self.usuarios = true;

      $http.post("/logout",{},{headers: {
        "Authorization": 'Basic ' + $cookies.get("Basic"),'Content-Type':'application/json'}
      }).error(function(data, status, headers, config) {
        console.log(data);

      }).then(function (data) {
        console.log(data);
      });

    });
  }
  self.GuardarCambios = function () {

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

    });
  }

  self.nuevoRol = function (rol) {

    var nuevo_rol = "ROLE_" + self.estudios[rol].studyName;
    if(self.roles == undefined || self.roles == ""){

        self.roles = nuevo_rol;
      self.actualizarEstudios();
    }else{

        self.roles = self.roles + ";" + nuevo_rol;
      self.actualizarEstudios();
    }

  }

  self.GuardarCambios = function () {
    // Hay cambios


    if(!self.cambiar()){

      self.modificado = undefined;
      self.modificado = {
        "userName": self.userName,
        "password": self.password,
        "email": self.email,
        "privileges": self.roles
      }

      $http.put('/user/privileges', self.modificado, {
        headers: {
          "Authorization": 'Basic ' + $cookies.get("Basic")
        }
      }).error(function (data, status, headers, config) {

      }).then(function (data) {
        console.log(data);

      });
      $http.put('/user/email', self.modificado, {
        headers: {
          "Authorization": 'Basic ' + $cookies.get("Basic")
        }
      }).error(function (data, status, headers, config) {

      }).then(function (data) {
        console.log(data);

      });
      $http.put('/user/password', self.modificado, {
        headers: {
          "Authorization": 'Basic ' + $cookies.get("Basic")
        }
      }).error(function (data, status, headers, config) {
        console.log(config);
      }).then(function (data) {
        console.log(data);

      });
      $window.location.href = '/#/cuentas';
    }else{

      // No hay cambios redirigir a listado de usuarios
      $window.location.href = '/#/cuentas';
    }
  }
}]);
