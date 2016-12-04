'use strict';

// Declare app level module which depends on views, and components
angular.module('frontEndApp', [
  'ngRoute','ngCookies','angularSpinners',
  'frontEndApp.aepd',
  'frontEndApp.login',
  'frontEndApp.estudio',
  'frontEndApp.registro',
  'frontEndApp.nav',
  'frontEndApp.cuentas',
  'frontEndApp.usuario',
  'frontEndApp.control',
  'frontEndApp.controlEstudio',
  'frontEndApp.controlUsuario',
  'frontEndApp.testCognitivo',
  'frontEndApp.testCognitivoResultado',
  'frontEndApp.lineaBase',
  'frontEndApp.actividad',
  'frontEndApp.controlSujeto',
  'frontEndApp.soportes',
  'frontEndApp.transporteSoportes',
  'frontEndApp.incidencias'

]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  //$locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/login'});
}]);
