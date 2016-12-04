'use strict';

angular.module('frontEndApp.auth', ['ngRoute','chart.js','base64'])
    .service('auth', ['$http','$base64','$cookies',function ($http, $base64,$cookies) {

        var self = this;
        self.logged = false;
        self.admin = false;
        self.token = undefined;  //Current user username
        self.role= undefined;
        self.observ = undefined ;

        self.getRoles = function () {
            return self.role;
        }

        self.recordObserb = function (callback) {

            self.observ = callback;
        }

        self.notify = function () {
            self.observ();
        }
        self.login = function(username,password,role){
            self.token = self.generateToken(username,password);

            $cookies.remove("Basic");

            $cookies.put("Basic",self.token);
            var auxi = role;
            var aux2 = auxi.split(";");
            self.role = aux2;
            self.logged = true;

            console.log(role);
            self.notify();

        };

        self.logout = function () {
            self.logged = false;
            $http.post("/logout",{},{'Authorization': 'Basic ' + $cookies.get("Basic"), 'Content-Type':'application/json'
            }).error(function(data, status, headers, config) {
                console.log(data);

            }).then(function (data) {
                console.log(data);
            });
            $cookies.remove("Basic");
            $cookies.remove("JSESSIONID");

            self.token = undefined;
            self.role = undefined;



            self.notify();

        }

        self.generateToken = function (username,password) {
            var tokenn = $base64.encode(username + ':' + password);
            return tokenn;
        }
        self.getToken = function () {
            return self.token;
        }
        self.isLog = function () {
            if($cookies.get("Basic") == undefined
                || $cookies.get("Basic") == null
                || $cookies.get("Basic") == ""){
                return false;
            }else{ return true}
        }
        self.isAdmin = function () {
            if(self.role == undefined){
                return false;
            }else{

                if(self.role.indexOf("ROLE_ADMIN") != -1){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }]);