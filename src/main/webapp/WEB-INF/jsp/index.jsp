<div class="row-fluid">
	<table class="table table-striped table-bordered span6 offset3" >
		<thead>
			<td></td>
			<td>Kombien Kil Sera ?</td>
			<td>Kiki veut la Pietra ?</td>
			<td>Kiki veut la Terrine ?</td>
		</thead>
		<tbody>
			<tr>
				<td>Moi ({{me.pseudo}})</td>
				<td>{{me.count}}</td>
				<td>{{me.pietra}}</td>
				<td>{{me.terrine}}</td>
			</tr>
			<tr>
				<td></td>
				<td><a class="btn" href="/incCount">+1</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn" href="/decCount">-1</a></td>
				<td><a class="btn" href="/incPietra">+1</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn" href="/decPietra">-1</a></td>
				<td><a class="btn" href="/incTerrine">+1</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn" href="/decTerrine">-1</a></td>
			</tr>
			<tr ng-repeat="other int others">
				<td>{{other.pseudo}}</td>
				<td>{{other.count}}</td>
				<td>{{other.pietra}}</td>
				<td>{{other.terrine}}</td>
			</tr>
		</tbody>
	</table>
</div>