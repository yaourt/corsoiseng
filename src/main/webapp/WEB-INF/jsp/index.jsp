<div class="row-fluid" ng-controller="CorsoiseNGCtrl">
  <div class="span8 offset2">
	<table class="table table-striped table-bordered" >
		<thead>
			<tr>
				<th colspan="2"></th>
				<th >Kombien Kil Sera ?</th>
				<th >Kiki veut la Pietra ?</th>
				<th >Kiki veut la Terrine ?</th>
			</tr>
		</thead>
		<tbody>
			<tr class="warning">
				<th colspan="2">Total</th>
				<th><p class="text-right">{{corsoisengData.sum.count}}</p></th>
				<th><p class="text-right">{{corsoisengData.sum.pietra}}</p></th>
				<th><p class="text-right">{{corsoisengData.sum.terrine}}</p></th>
			</tr>
			<tr class="info">
				<td style="width:  8%" class="vcenter"><a class="btn btn-mini disabled" title="Admin headshot !"><i class="icon-trash icon-white"></i></a>&nbsp;<a class="btn btn-mini" title="Cpa moi, m'a trompé" ng-click="deleteMe()"><i class="icon-remove icon-white"></i></a></td>
				<td style="width: 32%" class="vcenter">Moi<br /><span ng-bind-html-unsafe="corsoisengData.me.pseudo"</span></td>
				<td style="width: 20%"><p class="text-right">{{corsoisengData.me.count}}</p><p class="text-center"><a class="btn btn-mini" ng-click="incCount()"><i class="icon-plus icon-white"></i></a>&nbsp;<a class="btn btn-mini" ng-click="decCount()"><i class="icon-minus icon-white"></i></a></p></td>
				<td style="width: 20%"><p class="text-right">{{corsoisengData.me.pietra}}</p><p class="text-center"><a class="btn btn-mini" ng-click="incPietra()"><i class="icon-plus icon-white"></i></a>&nbsp;<a class="btn btn-mini" ng-click="decPietra()"><i class="icon-minus icon-white"></i></a></p></td>
				<td style="width: 20%"><p class="text-right">{{corsoisengData.me.terrine}}</p><p class="text-center"><a class="btn btn-mini" ng-click="incTerrine()"><i class="icon-plus icon-white"></i></a>&nbsp;<a class="btn btn-mini" ng-click="decTerrine()"><i class="icon-minus icon-white"></i></a></p></td>
			</tr>
			<tr ng-repeat="other in corsoisengData.others">
				<td class="vcenter"><a class="btn btn-mini disabled" title="Admin headshot !"><i class="icon-trash icon-white"></i> </a></td>
				<td class="vcenter"><span ng-bind-html-unsafe="other.pseudo"</span></td>
				<td><p class="text-right">{{other.count}}</p></td>
				<td><p class="text-right">{{other.pietra}}</p></td>
				<td><p class="text-right">{{other.terrine}}</p></td>
			</tr>
		</tbody>
	</table>
  </div>
</div>