<%-- 
    Document   : calendario
    Created on : 23-sep-2016, 10:46:22
    Author     : patriciabenitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>

    <!-- this page specific styles -->
    <link rel="stylesheet" href="/css/libs/fullcalendar.css" type="text/css" />
    <link rel="stylesheet" href="/css/libs/fullcalendar.print.css" type="text/css" media="print" />
    <link rel="stylesheet" href="/css/compiled/calendar.css" type="text/css" media="screen" />

</head>
<div class="container">

    <p>&nbsp;</p>
    <h1>Calendario de Reservaciones</h1>
    <div class="row">
        <div class="col-md-12">
            <div class="main-box">
                <div class="main-box-body clearfix">
                    <div id="calendar"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- this page specific scripts -->
<script src="/js/jquery-ui.custom.min.js"></script>
<script src="/js/moment.min.js"></script>
<script src="/js/fullcalendar.min.js"></script>

<!-- this page specific inline scripts -->
<script>
    $(document).ready(function () {
        /* initialize the external events
         -----------------------------------------------------------------*/

        $('#external-events div.external-event').each(function () {

            // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
            // it doesn't need to have a start or end
            var eventObject = {
                title: $.trim($(this).text()) // use the element's text as the event title
            };

            // store the Event Object in the DOM element so we can get to it later
            $(this).data('eventObject', eventObject);

            // make the event draggable using jQuery UI
            $(this).draggable({
                zIndex: 999,
                revert: true, // will cause the event to go back to its
                revertDuration: 0  //  original position after the drag
            });

        });


        /* initialize the calendar
         -----------------------------------------------------------------*/

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var calendar = $('#calendar').fullCalendar({
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            isRTL: $('body').hasClass('rtl'), //rtl support for calendar
            selectable: true,
            selectHelper: true,
            select: function (start, end, allDay) {
                var title = prompt('Event Title:');
                if (title) {
                    calendar.fullCalendar('renderEvent',
                            {
                                title: title,
                                start: start,
                                end: end,
                                allDay: allDay
                            },
                            true // make the event "stick"
                            );
                }
                calendar.fullCalendar('unselect');
            },
            editable: true,
            droppable: true, // this allows things to be dropped onto the calendar !!!
            drop: function (date, allDay) { // this function is called when something is dropped

                // retrieve the dropped element's stored Event Object
                var originalEventObject = $(this).data('eventObject');

                // we need to copy it, so that multiple events don't have a reference to the same object
                var copiedEventObject = $.extend({}, originalEventObject);

                // assign it the date that was reported
                copiedEventObject.start = date;
                copiedEventObject.allDay = allDay;

                // copy label class from the event object
                var labelClass = $(this).data('eventclass');

                if (labelClass) {
                    copiedEventObject.className = labelClass;
                }

                // render the event on the calendar
                // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
                $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

                // is the "remove after drop" checkbox checked?
                if ($('#drop-remove').is(':checked')) {
                    // if so, remove the element from the "Draggable Events" list
                    $(this).remove();
                }

            },
            events: [
    <s:iterator value="reservaciones" >
                {
                    title: '[<s:property value="departamento.nombre" escape="false" />]<s:property value="areaHorario.area.nombre" escape="false" />',
                    start: new Date(<s:date name="fecha" format="yyyy" />, (<s:date name="fecha" format="MM" /> - 1), <s:date name="fecha" format="dd" />, <s:property value="areaHorario.horaICalendario" /> ),
                    end: new Date(<s:date name="fecha" format="yyyy" />, (<s:date name="fecha" format="MM" /> - 1), <s:date name="fecha" format="dd" />, <s:property value="areaHorario.horaFCalendario" /> ),
                    className: 'label-success'
                },
    </s:iterator>
                
                
            ]
        });
    });
</script>