/**
 * Classe javascript que permite manipular o mapa da API V3 do Google Maps,
 * com funções necessárias ao escopo do TG.
 * @author André 
 */

var directionsDisplay;
var map;

/**
 * Inicializa o mapa do google maps
 * @param lat - latitude 
 * @param lng - longitude
 * @param contentString - Conteudo da janela de informações
 * @return Mapa
 */
function initialize(lat,lng,contentString) {
	//adiciona latitude e longitude ao mapa
	var myLatlng = new google.maps.LatLng(lat,lng);

	//instancia um objeto direction dislpay para ser usado na rota
	directionsDisplay = new google.maps.DirectionsRenderer();
	
	//opções basicas do mapa
	var myOptions = {
	  zoom: 17,
	  center: myLatlng,
	  mapTypeId: google.maps.MapTypeId.HYBRID
	};
	
	//instancia o mapa
	map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	//janela de informações basica
	var infowindow = new google.maps.InfoWindow({
	    content: contentString
	});
	
	//marca no mapa
	var marker = new google.maps.Marker({
	      position: myLatlng, 
	      map: map, 
	      title:"Clique para informações"
	  });   
	
	//adiciona a janela de informações
	google.maps.event.addListener(marker, 'click', function() {
		  infowindow.open(map,marker);
		});
	
	//seta o mapa da pagina no direction display
	directionsDisplay.setMap(map);
	directionsDisplay.setPanel(document.getElementById("directionsPanel"));
}

/**
 * Função que calcula a rota do usuário até a unidade de venda
 * @param latOrg - latitude da origem (Usuário)
 * @param lgnOrg - longitude da origem (Usuário)
 * @param latDest - latitude do destino (Unidade de venda)
 * @param lgnDest - longitude do destino (Unidade de venda)
 * @return Mapa calculado
 */
function calcRoute(latOrg, lgnOrg, latDest, lgnDest) {

	var origem = new google.maps.LatLng(latOrg, lgnOrg);
	var destino = new google.maps.LatLng(latDest, lgnDest);
	
	//Serviço de rotas.
	var directionsService = new google.maps.DirectionsService();

	//Objeto com origem,destino e tipo de viagem (Carro)
	var request = {
		origin : origem,
		destination : destino,
		travelMode : google.maps.DirectionsTravelMode.DRIVING
	};

	//Obtém a rota!
	directionsService.route(request, function(response, status) {
		if (status == google.maps.DirectionsStatus.OK) {
			directionsDisplay.setDirections(response);
		}
	});
}
