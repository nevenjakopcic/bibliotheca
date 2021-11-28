import Vue from 'vue';
import format from 'date-fns/format';
import parseISO from 'date-fns/parseISO';

Vue.filter('formatDate', function (value) {
  if (value) {
    return format(parseISO(value), 'dd.MM.yyyy.');
  }
});

Vue.filter('formatDateTime', function (value) {
  if (value) {
    return format(parseISO(value), 'dd.MM.yyyy. HH:mm');
  }
});

Vue.filter('formatTime', function (value) {
  if (value) {
    if (value.length <= 7) {
      var date = new Date();
      value = format(date, "yyyy-MM-dd") + " " + value;
    }
    return format(parseISO(value), 'HH:mm');
  }
});