// This file is generated by rust-protobuf 2.28.0. Do not edit
// @generated

// https://github.com/rust-lang/rust-clippy/issues/702
#![allow(unknown_lints)]
#![allow(clippy::all)]

#![allow(unused_attributes)]
#![cfg_attr(rustfmt, rustfmt::skip)]

#![allow(box_pointers)]
#![allow(dead_code)]
#![allow(missing_docs)]
#![allow(non_camel_case_types)]
#![allow(non_snake_case)]
#![allow(non_upper_case_globals)]
#![allow(trivial_casts)]
#![allow(unused_imports)]
#![allow(unused_results)]
//! Generated file from `src/proto/create-profile.proto`

/// Generated files are compatible only with the same version
/// of protobuf runtime.
// const _PROTOBUF_VERSION_CHECK: () = ::protobuf::VERSION_2_28_0;

#[derive(PartialEq,Clone,Default)]
pub struct CreateProfileRequest {
    // message fields
    pub name: ::std::string::String,
    pub email: ::std::string::String,
    // special fields
    pub unknown_fields: ::protobuf::UnknownFields,
    pub cached_size: ::protobuf::CachedSize,
}

impl<'a> ::std::default::Default for &'a CreateProfileRequest {
    fn default() -> &'a CreateProfileRequest {
        <CreateProfileRequest as ::protobuf::Message>::default_instance()
    }
}

impl CreateProfileRequest {
    pub fn new() -> CreateProfileRequest {
        ::std::default::Default::default()
    }

    // string name = 1;


    pub fn get_name(&self) -> &str {
        &self.name
    }
    pub fn clear_name(&mut self) {
        self.name.clear();
    }

    // Param is passed by value, moved
    pub fn set_name(&mut self, v: ::std::string::String) {
        self.name = v;
    }

    // Mutable pointer to the field.
    // If field is not initialized, it is initialized with default value first.
    pub fn mut_name(&mut self) -> &mut ::std::string::String {
        &mut self.name
    }

    // Take field
    pub fn take_name(&mut self) -> ::std::string::String {
        ::std::mem::replace(&mut self.name, ::std::string::String::new())
    }

    // string email = 2;


    pub fn get_email(&self) -> &str {
        &self.email
    }
    pub fn clear_email(&mut self) {
        self.email.clear();
    }

    // Param is passed by value, moved
    pub fn set_email(&mut self, v: ::std::string::String) {
        self.email = v;
    }

    // Mutable pointer to the field.
    // If field is not initialized, it is initialized with default value first.
    pub fn mut_email(&mut self) -> &mut ::std::string::String {
        &mut self.email
    }

    // Take field
    pub fn take_email(&mut self) -> ::std::string::String {
        ::std::mem::replace(&mut self.email, ::std::string::String::new())
    }
}

impl ::protobuf::Message for CreateProfileRequest {
    fn is_initialized(&self) -> bool {
        true
    }

    fn merge_from(&mut self, is: &mut ::protobuf::CodedInputStream<'_>) -> ::protobuf::ProtobufResult<()> {
        while !is.eof()? {
            let (field_number, wire_type) = is.read_tag_unpack()?;
            match field_number {
                1 => {
                    ::protobuf::rt::read_singular_proto3_string_into(wire_type, is, &mut self.name)?;
                },
                2 => {
                    ::protobuf::rt::read_singular_proto3_string_into(wire_type, is, &mut self.email)?;
                },
                _ => {
                    ::protobuf::rt::read_unknown_or_skip_group(field_number, wire_type, is, self.mut_unknown_fields())?;
                },
            };
        }
        ::std::result::Result::Ok(())
    }

    // Compute sizes of nested messages
    #[allow(unused_variables)]
    fn compute_size(&self) -> u32 {
        let mut my_size = 0;
        if !self.name.is_empty() {
            my_size += ::protobuf::rt::string_size(1, &self.name);
        }
        if !self.email.is_empty() {
            my_size += ::protobuf::rt::string_size(2, &self.email);
        }
        my_size += ::protobuf::rt::unknown_fields_size(self.get_unknown_fields());
        self.cached_size.set(my_size);
        my_size
    }

    fn write_to_with_cached_sizes(&self, os: &mut ::protobuf::CodedOutputStream<'_>) -> ::protobuf::ProtobufResult<()> {
        if !self.name.is_empty() {
            os.write_string(1, &self.name)?;
        }
        if !self.email.is_empty() {
            os.write_string(2, &self.email)?;
        }
        os.write_unknown_fields(self.get_unknown_fields())?;
        ::std::result::Result::Ok(())
    }

    fn get_cached_size(&self) -> u32 {
        self.cached_size.get()
    }

    fn get_unknown_fields(&self) -> &::protobuf::UnknownFields {
        &self.unknown_fields
    }

    fn mut_unknown_fields(&mut self) -> &mut ::protobuf::UnknownFields {
        &mut self.unknown_fields
    }

    fn as_any(&self) -> &dyn (::std::any::Any) {
        self as &dyn (::std::any::Any)
    }
    fn as_any_mut(&mut self) -> &mut dyn (::std::any::Any) {
        self as &mut dyn (::std::any::Any)
    }
    fn into_any(self: ::std::boxed::Box<Self>) -> ::std::boxed::Box<dyn (::std::any::Any)> {
        self
    }

    fn descriptor(&self) -> &'static ::protobuf::reflect::MessageDescriptor {
        Self::descriptor_static()
    }

    fn new() -> CreateProfileRequest {
        CreateProfileRequest::new()
    }

    fn descriptor_static() -> &'static ::protobuf::reflect::MessageDescriptor {
        static descriptor: ::protobuf::rt::LazyV2<::protobuf::reflect::MessageDescriptor> = ::protobuf::rt::LazyV2::INIT;
        descriptor.get(|| {
            let mut fields = ::std::vec::Vec::new();
            fields.push(::protobuf::reflect::accessor::make_simple_field_accessor::<_, ::protobuf::types::ProtobufTypeString>(
                "name",
                |m: &CreateProfileRequest| { &m.name },
                |m: &mut CreateProfileRequest| { &mut m.name },
            ));
            fields.push(::protobuf::reflect::accessor::make_simple_field_accessor::<_, ::protobuf::types::ProtobufTypeString>(
                "email",
                |m: &CreateProfileRequest| { &m.email },
                |m: &mut CreateProfileRequest| { &mut m.email },
            ));
            ::protobuf::reflect::MessageDescriptor::new_pb_name::<CreateProfileRequest>(
                "CreateProfileRequest",
                fields,
                file_descriptor_proto()
            )
        })
    }

    fn default_instance() -> &'static CreateProfileRequest {
        static instance: ::protobuf::rt::LazyV2<CreateProfileRequest> = ::protobuf::rt::LazyV2::INIT;
        instance.get(CreateProfileRequest::new)
    }
}

impl ::protobuf::Clear for CreateProfileRequest {
    fn clear(&mut self) {
        self.name.clear();
        self.email.clear();
        self.unknown_fields.clear();
    }
}

impl ::std::fmt::Debug for CreateProfileRequest {
    fn fmt(&self, f: &mut ::std::fmt::Formatter<'_>) -> ::std::fmt::Result {
        ::protobuf::text_format::fmt(self, f)
    }
}

impl ::protobuf::reflect::ProtobufValue for CreateProfileRequest {
    fn as_ref(&self) -> ::protobuf::reflect::ReflectValueRef {
        ::protobuf::reflect::ReflectValueRef::Message(self)
    }
}

#[derive(PartialEq,Clone,Default)]
pub struct CreateProfileResponse {
    // message oneof groups
    pub optional_error: ::std::option::Option<CreateProfileResponse_oneof_optional_error>,
    // special fields
    pub unknown_fields: ::protobuf::UnknownFields,
    pub cached_size: ::protobuf::CachedSize,
}

impl<'a> ::std::default::Default for &'a CreateProfileResponse {
    fn default() -> &'a CreateProfileResponse {
        <CreateProfileResponse as ::protobuf::Message>::default_instance()
    }
}

#[derive(Clone,PartialEq,Debug)]
pub enum CreateProfileResponse_oneof_optional_error {
    error(::std::string::String),
}

impl CreateProfileResponse {
    pub fn new() -> CreateProfileResponse {
        ::std::default::Default::default()
    }

    // string error = 1;


    pub fn get_error(&self) -> &str {
        match self.optional_error {
            ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(ref v)) => v,
            _ => "",
        }
    }
    pub fn clear_error(&mut self) {
        self.optional_error = ::std::option::Option::None;
    }

    pub fn has_error(&self) -> bool {
        match self.optional_error {
            ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(..)) => true,
            _ => false,
        }
    }

    // Param is passed by value, moved
    pub fn set_error(&mut self, v: ::std::string::String) {
        self.optional_error = ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(v))
    }

    // Mutable pointer to the field.
    pub fn mut_error(&mut self) -> &mut ::std::string::String {
        if let ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(_)) = self.optional_error {
        } else {
            self.optional_error = ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(::std::string::String::new()));
        }
        match self.optional_error {
            ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(ref mut v)) => v,
            _ => panic!(),
        }
    }

    // Take field
    pub fn take_error(&mut self) -> ::std::string::String {
        if self.has_error() {
            match self.optional_error.take() {
                ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(v)) => v,
                _ => panic!(),
            }
        } else {
            ::std::string::String::new()
        }
    }
}

impl ::protobuf::Message for CreateProfileResponse {
    fn is_initialized(&self) -> bool {
        true
    }

    fn merge_from(&mut self, is: &mut ::protobuf::CodedInputStream<'_>) -> ::protobuf::ProtobufResult<()> {
        while !is.eof()? {
            let (field_number, wire_type) = is.read_tag_unpack()?;
            match field_number {
                1 => {
                    if wire_type != ::protobuf::wire_format::WireTypeLengthDelimited {
                        return ::std::result::Result::Err(::protobuf::rt::unexpected_wire_type(wire_type));
                    }
                    self.optional_error = ::std::option::Option::Some(CreateProfileResponse_oneof_optional_error::error(is.read_string()?));
                },
                _ => {
                    ::protobuf::rt::read_unknown_or_skip_group(field_number, wire_type, is, self.mut_unknown_fields())?;
                },
            };
        }
        ::std::result::Result::Ok(())
    }

    // Compute sizes of nested messages
    #[allow(unused_variables)]
    fn compute_size(&self) -> u32 {
        let mut my_size = 0;
        if let ::std::option::Option::Some(ref v) = self.optional_error {
            match v {
                &CreateProfileResponse_oneof_optional_error::error(ref v) => {
                    my_size += ::protobuf::rt::string_size(1, &v);
                },
            };
        }
        my_size += ::protobuf::rt::unknown_fields_size(self.get_unknown_fields());
        self.cached_size.set(my_size);
        my_size
    }

    fn write_to_with_cached_sizes(&self, os: &mut ::protobuf::CodedOutputStream<'_>) -> ::protobuf::ProtobufResult<()> {
        if let ::std::option::Option::Some(ref v) = self.optional_error {
            match v {
                &CreateProfileResponse_oneof_optional_error::error(ref v) => {
                    os.write_string(1, v)?;
                },
            };
        }
        os.write_unknown_fields(self.get_unknown_fields())?;
        ::std::result::Result::Ok(())
    }

    fn get_cached_size(&self) -> u32 {
        self.cached_size.get()
    }

    fn get_unknown_fields(&self) -> &::protobuf::UnknownFields {
        &self.unknown_fields
    }

    fn mut_unknown_fields(&mut self) -> &mut ::protobuf::UnknownFields {
        &mut self.unknown_fields
    }

    fn as_any(&self) -> &dyn (::std::any::Any) {
        self as &dyn (::std::any::Any)
    }
    fn as_any_mut(&mut self) -> &mut dyn (::std::any::Any) {
        self as &mut dyn (::std::any::Any)
    }
    fn into_any(self: ::std::boxed::Box<Self>) -> ::std::boxed::Box<dyn (::std::any::Any)> {
        self
    }

    fn descriptor(&self) -> &'static ::protobuf::reflect::MessageDescriptor {
        Self::descriptor_static()
    }

    fn new() -> CreateProfileResponse {
        CreateProfileResponse::new()
    }

    fn descriptor_static() -> &'static ::protobuf::reflect::MessageDescriptor {
        static descriptor: ::protobuf::rt::LazyV2<::protobuf::reflect::MessageDescriptor> = ::protobuf::rt::LazyV2::INIT;
        descriptor.get(|| {
            let mut fields = ::std::vec::Vec::new();
            fields.push(::protobuf::reflect::accessor::make_singular_string_accessor::<_>(
                "error",
                CreateProfileResponse::has_error,
                CreateProfileResponse::get_error,
            ));
            ::protobuf::reflect::MessageDescriptor::new_pb_name::<CreateProfileResponse>(
                "CreateProfileResponse",
                fields,
                file_descriptor_proto()
            )
        })
    }

    fn default_instance() -> &'static CreateProfileResponse {
        static instance: ::protobuf::rt::LazyV2<CreateProfileResponse> = ::protobuf::rt::LazyV2::INIT;
        instance.get(CreateProfileResponse::new)
    }
}

impl ::protobuf::Clear for CreateProfileResponse {
    fn clear(&mut self) {
        self.optional_error = ::std::option::Option::None;
        self.unknown_fields.clear();
    }
}

impl ::std::fmt::Debug for CreateProfileResponse {
    fn fmt(&self, f: &mut ::std::fmt::Formatter<'_>) -> ::std::fmt::Result {
        ::protobuf::text_format::fmt(self, f)
    }
}

impl ::protobuf::reflect::ProtobufValue for CreateProfileResponse {
    fn as_ref(&self) -> ::protobuf::reflect::ReflectValueRef {
        ::protobuf::reflect::ReflectValueRef::Message(self)
    }
}

static file_descriptor_proto_data: &'static [u8] = b"\
    \n\x1esrc/proto/create-profile.proto\x12\x07profile\"@\n\x14CreateProfil\
    eRequest\x12\x12\n\x04name\x18\x01\x20\x01(\tR\x04name\x12\x14\n\x05emai\
    l\x18\x02\x20\x01(\tR\x05email\"A\n\x15CreateProfileResponse\x12\x16\n\
    \x05error\x18\x01\x20\x01(\tH\0R\x05errorB\x10\n\x0eoptional_errorb\x06p\
    roto3\
";

static file_descriptor_proto_lazy: ::protobuf::rt::LazyV2<::protobuf::descriptor::FileDescriptorProto> = ::protobuf::rt::LazyV2::INIT;

fn parse_descriptor_proto() -> ::protobuf::descriptor::FileDescriptorProto {
    ::protobuf::Message::parse_from_bytes(file_descriptor_proto_data).unwrap()
}

pub fn file_descriptor_proto() -> &'static ::protobuf::descriptor::FileDescriptorProto {
    file_descriptor_proto_lazy.get(|| {
        parse_descriptor_proto()
    })
}
