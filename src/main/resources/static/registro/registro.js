'use strict';

angular.module('frontEndApp.registro', ['ngRoute','frontEndApp.auth'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/registro', {
            templateUrl: 'registro/registro.html',
            controller: 'RegistroCtrl',
            controllerAs: 'registroCtrl'
        });
    }])

    .controller('RegistroCtrl', ['$cookies','$route', '$location',
        '$window', '$http','$routeParams','auth','spinnerService',
        function ($cookies, $route, $location, $window, $http,$routeParams,auth,spinnerService) {
        var self = this;
        if($cookies.get("Basic") == undefined
            || $cookies.get("Basic") == null
            || $cookies.get("Basic") == ""){
            $location.path("/login");
        }
            self.dis = false;
        /* Indica que parte del formulario se muestra en cada momento.
         * 0 para mostrar los datos de identificaión.
         * 1 para mostrar los datos fisiológicos.
         * 2 para mostrar los datos de los hábitos tóxicos.
         * 3 para mostrar otros hábitos.
         * 4 para mostrar los datos psicológicos.
         */
        self.mostrar = 0;


        /**
         * Inicializar los input radiales.
         */
        self.genero = "Hombre";
        self.tomaMedicacion = "No";
        self.menstruacion = "No";
        self.dificultadSensorial = "No";
        self.dolor = "No";
        self.fumador = "No";
        self.consumoOtrasSustancias = "No";
        self.bebidasAlcoholicas = "No";
        self.consumidoUltimasHoras = "No"
        self.deporte = "No";
        self.submarinismo = "No";
        self.dificultadParaDormir = "No";
        self.cafeina = "No";
        self.bebidasConGas = "No";
        self.ansiedad = "No";
        self.animoBajo = "No";

        /**
         * Metodos para elegir que formulario se muestra.
         */
        self.habitosToxicos = function () {
            self.mostrar = 2;
            $window.scrollTo(0, 0);

        }
        self.datosIdentificacion = function () {
            self.mostrar = 0;
            $window.scrollTo(0, 0);

        }
        self.datosFisiologicos = function () {
            self.mostrar = 1;
            $window.scrollTo(0, 0);
            self.d = new Date(self.dia);
            self.e = new Date(self.hora);
            self.d.setHours(self.e.getHours());
            self.d.setMinutes(self.e.getMinutes());
            self.fechaMiligundos = self.d.getTime();

        }
        self.otrosHabitos = function () {
            self.mostrar = 3;
            $window.scrollTo(0, 0);
        }
        self.datosPsicologicos = function () {
            self.mostrar = 4;
            $window.scrollTo(0, 0);
        }


        /**
         * Metodos para deshabilitar determinadas opciones del formulario.
         */
        self.pedirPautas = function () {

            if (self.tomaMedicacion == "Si") {
                return false;
            } else {
                return true;
            }
        }
        self.esHombre = function () {
            if (self.genero == "Hombre") {
                return true;
            }
            else {
                return false;
            }
        }
        self.tieneDificultadesSensoriales = function () {
            if (self.dificultadSensorial == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.tieneDolor = function () {
            if (self.dolor == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.esFumador = function () {
            if (self.fumador == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.consumeOtrasSustancias = function () {
            if (self.consumoOtrasSustancias == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.consumeAlcohol = function () {
            if (self.bebidasAlcoholicas == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.consumeCafeina = function () {
            if (self.cafeina == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.consumeBebidaConGas = function () {
            if (self.bebidasConGas == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.haConsumidoUltimasHoras = function () {
            if (self.consumidoUltimasHoras == "Si") {
                return true;
            } else {
                return false;
            }
        }
        self.haceDeporte = function () {
            if (self.deporte == "Si") {
                return true;
            } else {
                return false;
            }
        }

        self.haceSubmarinismo = function () {
            if (self.submarinismo == "Si") {
                return true;
            } else {
                return false;
            }
        }

        self.tieneDificultadesParaDormir = function () {
            if (self.dificultadParaDormir == "Si") {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Metodos para Convertir los formularios en json
         */

        /**
         * Convertir habitos toxicos en JSON.
         */

        self.convertirHabitosToxicos = function () {
            var toxicos = {
                'smoker': false,
                'dayCigarettes': self.cigarrillosDia,
                'otherSubstances': true,
                'otherSubstancesGr': self.gramosDiaOtrasSustancias,
                'alcoholicDrinks': false,
                'litresOfAlcoholicDrinks': self.litrosBebidaAlcoholica
            }
            if (self.esFumador()) {
                toxicos.smoker = true;

            } else {
                toxicos.dayCigarettes = 0;
            }
            if (self.consumeAlcohol()) {
                toxicos.alcoholicDrinks = true;
            } else {
                toxicos.litresOfAlcoholicDrinks = 0;
            }
            if (self.consumeOtrasSustancias()) {
                toxicos.otherSubstances = true;
            } else {
                toxicos.otherSubstancesGr = 0;

            }
            return toxicos;
        }
        /**
         * Convertir otros habitos en json.
         */

        self.convertirOtrosHabitos = function () {
            var otros = {
                'caffeineConsumption': false,
                'caffeineConsumptionGrams': self.cantidadCafeina,
                'carbonatedDrinks': false,
                'carbonatedDrinksLitres': self.cantidadBebidaConGas,
                'consumedInTheLasHours': false,
                'whatConsumedInTheLastHours': self.tipoConsumicion,
                'playSports': false,
                'kindOfSports': self.tipoDeporte,
                'timesPerWeekPlaySports': self.numDeporteSemanal,
                'playDiving': false,
                'experienceDivingAges': self.experienciaSubmarinismo,
                'inmersionsPerYear': self.inmersionesAnuales,
                'sleepingHours': self.horasSueno,
                'reallyBeSleep': self.horasSuenoReparador,
                'difficultySleeping': false,
                'difficultySleepingDescription': self.dificultadesDormir,
                'treatmentForSleepProblems': self.tratamientoProblemasSueno
            }
            if (self.consumeCafeina()) {
                otros.caffeineConsumption = true;
            } else {
                otros.caffeineConsumptionGrams = 0;
            }
            if (self.consumeBebidaConGas()) {
                otros.carbonatedDrinks = true;
            } else {
                otros.carbonatedDrinksLitres = 0;
            }
            if (self.haConsumidoUltimasHoras()) {
                otros.consumedInTheLasHours = true;
            }
            if (self.haceSubmarinismo()) {
                otros.playDiving = true;
            } else {
                otros.experienceDivingAges = 0;
                otros.inmersionsPerYear = 0;
            }
            if (self.tieneDificultadesParaDormir()) {
                otros.difficultySleeping = true;
            }
            return otros;
        }

        self.convertirDatosPsicologicos = function () {
            var psicologicos = {
                'anxietyEpisodes': false,
                'lowMood': false,
                'currentNerves': self.nerviosActuales,
                'calm': self.sentimientoTranquilidad,
                'currentStat': self.estadoActual
            }
            if (self.ansiedad == 'Si') {
                psicologicos.anxietyEpisodes = true;
            }
            if (self.animoBajo == 'Si') {
                psicologicos.lowMood = true;
            }
            return psicologicos;
        }

        self.convertirDatosFisiologicos = function () {
            var fisiologicos = {
                'heightCm': self.altura,
                'weigthKge': self.peso,
                'medication': false,
                'treatment': self.pautasMedicacion,
                'menstruation': false,
                'painfulMenstruation': self.menstruacionDolorosa,
                'menstruationTreatment': self.tratamientoMenstruacion,
                'pulsations': self.pulsaciones,
                'bloodPressure': self.presionArterial,
                'sensoryDifficulties': false,
                'sensoryDifficultiesDescription': self.descripcionDificultadSensorial,
                'pain': false,
                'periodPain': 'M',
                'painZone': self.descripcionZonaDolor,
                'degreeOfPain': self.cantidadDolor,
                'feelingOfHealth': self.sentimientoSalud,
                'nuisanceDescription': self.molestias
            }
            if (self.menstruacion == "Si") {
                fisiologicos.menstruation = true;
            }
            if (self.tomaMedicacion == "Si") {
                fisiologicos.medication = true;
            }
            if (self.tieneDificultadesSensoriales()) {
                fisiologicos.sensoryDifficulties = true;
            }
            if (self.periodoDolor == "T") {
                fisiologicos.periodPain = "T";
            } else if (self.periodoDolor == "N") {
                fisiologicos.periodPain = "N";
            }
            return fisiologicos;
        }
        /*
         * Fecha estatica, cambiarla a milli-segundos
         */
        self.convertirDatosRegistro = function () {

            var reg = {
                'personalCode':self.codigoPersonal,
                'age':self.edad,
                'gender':self.genero,
                'checkIn':self.fechaMiligundos,
                'device':self.dispositivo,
                'balanced':self.balanceado,
                'studyName': {'studyName':$routeParams.nombre}
            }
            return reg;
        }
        self.registryJSON = function () {
            var json = {
                'subject':self.convertirDatosRegistro(),
                'toxicHabits': self.convertirHabitosToxicos(),
                'otherHabits': self.convertirOtrosHabitos(),
                'physiologicalData': self.convertirDatosFisiologicos(),
                'psychologicalData': self.convertirDatosPsicologicos()
            }
            return json;
        }
        self.submit = function () {

            self.dis = true;
            spinnerService.show('loadingSpinner');
            $http.post('/subject/', self.registryJSON(), {
                headers: {
                    "Authorization": 'Basic ' + $cookies.get("Basic"),
                    "Content-Type": "application/json"
                }
            }).error(function (data, status, headers, config) {
                self.dis = false;

            }).then(function (data) {
                console.log(data);

                spinnerService.hide('loadingSpinner');
                $window.location.href = '/#/controlEstudio?nombre='
                    + $routeParams.nombre;
            });
        }
        self.disab = function () {
            return self.dis;
        }
    }]);
