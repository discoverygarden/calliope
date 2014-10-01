/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 1.3.40
 * 
 * This file is not intended to be easily readable and contains a number of 
 * coding conventions designed to improve portability and efficiency. Do not make
 * changes to this file unless you know what you are doing--modify the SWIG 
 * interface file instead. 
 * ----------------------------------------------------------------------------- */

#ifndef PHP_HRIT_H
#define PHP_HRIT_H

extern zend_module_entry hrit_module_entry;
#define phpext_hrit_ptr &hrit_module_entry

#ifdef PHP_WIN32
# define PHP_HRIT_API __declspec(dllexport)
#else
# define PHP_HRIT_API
#endif

#ifdef ZTS
#include "TSRM.h"
#endif

PHP_MINIT_FUNCTION(hrit);
PHP_MSHUTDOWN_FUNCTION(hrit);
PHP_RINIT_FUNCTION(hrit);
PHP_RSHUTDOWN_FUNCTION(hrit);
PHP_MINFO_FUNCTION(hrit);

ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_error_string_set);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_error_string_get);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_formats_set);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_formats_get);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_num_formats_set);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_num_formats_get);
ZEND_NAMED_FUNCTION(_wrap_new_hrit_formatter);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_load_markup);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_get_html_len);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_load_css);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_convert);
ZEND_NAMED_FUNCTION(_wrap_hrit_formatter_hrit_list);
#endif /* PHP_HRIT_H */