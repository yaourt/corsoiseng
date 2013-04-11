<div class="row-fluid">
	<form action="/signIn" method="post" class="span6 offset3 form-horizontal">
			<legend>Mais t'es qui toi ?!?</legend>
			<div class="control-group">
				<label class="control-label" for="pseudo">Ton pseudo</label>
				<div class="controls">
					<input
						type="text"
						placeholder="Bon vas-y tape ici ..."
						class="input-large"
						name="pseudo"
						id="pseudo">
					<span class="help-block">Limitations :
					<!-- Help button -->
						<a title="Help" class="btn btn-mini" data-toggle="modal" href="#modalHelp"><i class="icon-question-sign icon-white"></i></a>
					</span>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<button type="submit" class="btn btn-primary btn-mini">Oh oui, soumets-moi !</button>
				</div>
			</div>
	</form>
</div>