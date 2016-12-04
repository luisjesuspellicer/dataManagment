'use strict';

angular.module('frontEndApp.aepd', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/aepd', {
    templateUrl: 'AEPD/aepd.html',
    controller: 'AepdCtrl',
    controllerAs: 'aepdCtrl'
  });
}])

.controller('AepdCtrl', [function() {

  var self = this;
  self.mostrar = 0;

  self.declaracion = function () {
    self.mostrar = 1;
    $window.scrollTo(0, 0);
  }
  self.derechos = function () {
    self.mostrar = 2;
    $window.scrollTo(0, 0);
  }
  self.volverDeclaracion = function (){
    self.mostrar = 0;
    $window.scrollTo(0, 0);
  }
}]);
