(function ($) {
    $(document).ready(function () {
        let $confirmDialog = $('#confirmDialog');
        $confirmDialog.on('show.bs.modal', function (event) {
            console.log(event);

            const $button = $(event.relatedTarget);
            const $delete = $confirmDialog.find('button[data-role=delete]');
            $delete.click(function () {
                const $form = $('<form action="' + $button.data('url') + '" method="post"></form>');
                $('body').append($form);
                $form.submit();
            });
        })
    });
})(jQuery);